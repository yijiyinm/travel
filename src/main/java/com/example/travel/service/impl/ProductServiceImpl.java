package com.example.travel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.dto.AddProductDTO;
import com.example.travel.dto.ProductPriceDTO;
import com.example.travel.dao.ProductMapper;
import com.example.travel.dao.entity.ProductDO;
import com.example.travel.dao.entity.ProductImgDO;
import com.example.travel.dao.entity.ProductPriceDO;
import com.example.travel.enums.LabelEnum;
import com.example.travel.enums.OrderStatusEnum;
import com.example.travel.service.OrderService;
import com.example.travel.service.ProductImgService;
import com.example.travel.service.ProductPriceService;
import com.example.travel.service.ProductService;
import com.example.travel.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yijiyin
 */
@Slf4j
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

    @Autowired
    private ProductImgService productImgService;
    @Autowired
    private ProductPriceService productPriceService;
    @Autowired
    private OrderService orderService;

    @Override
    public boolean addProduct(AddProductDTO addProductDTO) {
        log.info("商品新增参数:{}",addProductDTO);
        try {
            String productCode = GenerateCodeUtil.createCode(12);
            // 产品主表信息入库
            ProductDO productDO = new ProductDO();
            productDO.setProductCode(productCode);
            productDO.setProductName(addProductDTO.getProductName());
            productDO.setDescription(addProductDTO.getDescription());
            productDO.setStatus(OrderStatusEnum.WAIT_PAY.getStatus());
            productDO.setLabel(addProductDTO.getLabel());
            productDO.setSequence(addProductDTO.getSequence());
            productDO.setMainUrl(addProductDTO.getMainUrl());
            productDO.setMonthSalesNum(addProductDTO.getMonthSalesNum());
            productDO.setAdvanceDayNum(addProductDTO.getAdvanceDayNum());
            productDO.insert();

            // 图片信息存储
            if (addProductDTO.getUrls() != null){
                for(String url : addProductDTO.getUrls()){
                    ProductImgDO productImgDO = new ProductImgDO();
                    productImgDO.setProductCode(productCode);
                    productImgDO.setImgUrl(url);
                    productImgDO.insert();
                }
            }

            // 每日/对应价格存储
            if (addProductDTO.getPriceDTOS() !=null ){
                for (ProductPriceDTO priceDTO : addProductDTO.getPriceDTOS()){
                    log.info("价格存入");
                    ProductPriceDO priceDO = new ProductPriceDO();
                    priceDO.setProductCode(productCode);
                    priceDO.setDayDate(priceDTO.getDayDate());
                    priceDO.setPrice(priceDTO.getPrice());
                    priceDO.setPriceCr(priceDTO.getPriceCr());
                    priceDO.setPriceEt(priceDTO.getPriceEt());
                    priceDO.setInventory(priceDTO.getInventory());
                    priceDO.insert();
                }
            }

        } catch (Exception e) {
            log.error("产品新增异常："+e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateProduct(AddProductDTO addProductDTO) {
        log.info("编辑商品信息：{}",addProductDTO);
        try {
            // 产品主表信息入库
            ProductDO productDO = getOne(Wrappers.<ProductDO>lambdaQuery().eq(ProductDO::getProductCode, addProductDTO.getProductCode()));
            if (productDO == null) {
                log.error("数据异常，商品不存在");
                return false;
            }
            productDO.setProductName(addProductDTO.getProductName());
            productDO.setDescription(addProductDTO.getDescription());
            productDO.setLabel(addProductDTO.getLabel());
            productDO.setSequence(addProductDTO.getSequence());
            productDO.setMainUrl(addProductDTO.getMainUrl());
            productDO.setMonthSalesNum(addProductDTO.getMonthSalesNum());
            productDO.setAdvanceDayNum(addProductDTO.getAdvanceDayNum());
            productDO.updateById();

            // 删除之前的照片 重新保存
            productImgService.deleteInfoByCode(addProductDTO.getProductCode());
            // 图片信息存储
            if(addProductDTO.getUrls()!=null){
                for(String url :addProductDTO.getUrls()){
                    ProductImgDO productImgDO = new ProductImgDO();
                    productImgDO.setProductCode(productDO.getProductCode());
                    productImgDO.setImgUrl(url);
                    productImgDO.insert();
                }
            }


            if(addProductDTO.getPriceDTOS()!=null){
                // 删除当前时间及之后的日期数据 再保存
                // 今天的日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                date = calendar.getTime();
                String day = sdf.format(date);
                productPriceService.deletePriceInfoByDay(addProductDTO.getProductCode(),day);

                // 每日/对应价格存储
                for (ProductPriceDTO priceDTO : addProductDTO.getPriceDTOS()){
                    ProductPriceDO priceDO = new ProductPriceDO();
                    priceDO.setProductCode(productDO.getProductCode());
                    log.info("商品价格日期：{}",priceDTO.getDayDate());
                    priceDO.setDayDate(priceDTO.getDayDate());
                    priceDO.setPrice(priceDTO.getPrice());
                    priceDO.setPriceCr(priceDTO.getPriceCr());
                    priceDO.setPriceEt(priceDTO.getPriceEt());
                    priceDO.setInventory(priceDTO.getInventory());
                    log.info("修改的商品价格信息:{}"+priceDO);
                    priceDO.insert();
                }
            }

        } catch (Exception e) {
            log.error("产品新增异常：{}",e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateProductStatus(String productCode, Integer status) {
        ProductDO productDO = getOne(Wrappers.<ProductDO>lambdaQuery().eq(ProductDO::getProductCode, productCode));
        if(productDO == null){
            log.error("无此商品信息:{}"+productCode);
            return false;
        }
        productDO.setStatus(status);
        productDO.updateById();
        return true;
    }

    @Override
    public boolean deleteProduct(String productCode) {
        log.info("删除商品编码:{}",productCode);
        ProductDO productDO = getOne(Wrappers.<ProductDO>lambdaQuery().eq(ProductDO::getProductCode, productCode));
        if (productDO != null && (OrderStatusEnum.FAILURE_PAY.getStatus().equals(productDO.getStatus())||OrderStatusEnum.WAIT_PAY.getStatus().equals(productDO.getStatus()))){
            productDO.setStatus(OrderStatusEnum.DELETE_STATUS.getStatus());
            log.info("删除商品编码信息：{}",productDO);
            return productDO.updateById();
        }
        return false;
    }

    @Override
    public Page<AddProductDTO> getProductList(AddProductDTO addProductDTO) {
        try {
            Page<ProductDO> page = new Page(addProductDTO.getCurrent(),addProductDTO.getSize());
            Page<ProductDO> productDOPage = page(page,Wrappers.<ProductDO>lambdaQuery().like(StringUtils.isNotBlank(addProductDTO.getProductName()),ProductDO::getProductName, addProductDTO.getProductName())
                    .ne(ProductDO::getStatus,OrderStatusEnum.DELETE_STATUS.getStatus())
                    .orderByDesc(ProductDO::getSequence,ProductDO::getCreateDate));
            List<ProductDO> productDOS = productDOPage.getRecords();
            List<AddProductDTO> addProductDTOS = new ArrayList<>();
            for (ProductDO productDO : productDOS) {
                AddProductDTO addProductDTO1 = new AddProductDTO();
                addProductDTO1.setDescription(productDO.getDescription());
                addProductDTO1.setProductName(productDO.getProductName());
                addProductDTO1.setProductCode(productDO.getProductCode());
                addProductDTO1.setStatus(productDO.getStatus());
                addProductDTO1.setLabel(productDO.getLabel());
                addProductDTO1.setLabelName(LabelEnum.getNameByCode(productDO.getLabel()));
                addProductDTO1.setSequence(productDO.getSequence());
                addProductDTO1.setMonthSalesNum(productDO.getMonthSalesNum());
                addProductDTOS.add(addProductDTO1);
            }
            Page<AddProductDTO> addProductDTOPage = new Page<>();
            addProductDTOPage.setTotal(productDOPage.getTotal());
            addProductDTOPage.setRecords(addProductDTOS);
            return addProductDTOPage;
        } catch (Exception e) {
            log.error("商品分页查询异常:{}",e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AddProductDTO getProductDetail(String productCode) {
        ProductDO productDO = getOne(Wrappers.<ProductDO>lambdaQuery().eq(ProductDO::getProductCode, productCode));
        AddProductDTO addProductDTO = new AddProductDTO();
        addProductDTO.setDescription(productDO.getDescription());
        addProductDTO.setProductName(productDO.getProductName());
        addProductDTO.setProductCode(productDO.getProductCode());
        addProductDTO.setLabel(productDO.getLabel());
        addProductDTO.setLabelName(LabelEnum.getNameByCode(productDO.getLabel()));

        addProductDTO.setSequence(productDO.getSequence());
        addProductDTO.setMainUrl(productDO.getMainUrl());
        addProductDTO.setStatus(productDO.getStatus());
        addProductDTO.setMonthSalesNum(productDO.getMonthSalesNum());
        addProductDTO.setAdvanceDayNum(productDO.getAdvanceDayNum());

        // 价格信息
        List<ProductPriceDTO> productPriceDTOS = new ArrayList<>();
        // 今天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        date = calendar.getTime();
        String day = sdf.format(date);
        List<ProductPriceDO> productPriceDOS = productPriceService.getPriceInfoByDay(productCode,day);
        for (ProductPriceDO priceDO : productPriceDOS){
            log.info("商品价格信息：{}",productDO);
            ProductPriceDTO productPriceDTO = new ProductPriceDTO();
            productPriceDTO.setDayDate(priceDO.getDayDate());
            productPriceDTO.setPrice(priceDO.getPrice());
            productPriceDTO.setPriceCr(priceDO.getPriceCr());
            productPriceDTO.setPriceEt(priceDO.getPriceEt());
            productPriceDTO.setInventory(priceDO.getInventory());
            Integer inventory = priceDO.getInventory();
            // 剩余库存计算  查询对应当天订单数量总数进行扣除
            if (inventory == null) {
                inventory = 0;
            }
            Integer num = orderService.getDaySumByProductCode(productCode,priceDO.getDayDate());
            log.info("该商品已下单数量:{}",num);
            productPriceDTO.setInventoryLeftover(inventory-num);
            if (productPriceDTO.getInventoryLeftover()<0) {
                productPriceDTO.setInventoryLeftover(0);
            }
            productPriceDTOS.add(productPriceDTO);
        }
        if (productPriceDOS !=null && productPriceDOS.size()>0) {
            addProductDTO.setPrice(productPriceDOS.get(0).getPrice());
            addProductDTO.setPriceCr(productPriceDOS.get(0).getPriceCr());
        }
        addProductDTO.setPriceDTOS(productPriceDTOS);

        // 图片信息
        List<String> urls = productImgService.getUrlsByProductCode(productCode);
        addProductDTO.setUrls(urls);
        return addProductDTO;
    }

    @Override
    public List<String> getProductAllTableWX() {
        ArrayList<String> strings = new ArrayList<>();
        for (LabelEnum l:LabelEnum.values()){
            strings.add(l.getName());
        }

        return strings;
    }

    @Override
    public List<AddProductDTO> getProductListWX(String label) {
        List<ProductDO> productDOS = list(Wrappers.<ProductDO>lambdaQuery().eq(ProductDO::getLabel, label).eq(ProductDO::getStatus,OrderStatusEnum.ALREADY_PAY.getStatus()).orderByAsc(ProductDO::getSequence));
        List<AddProductDTO> addProductDTOS = new ArrayList<>();
        for (ProductDO productDO : productDOS) {
            AddProductDTO addProductDTO = new AddProductDTO();
            addProductDTO.setDescription(productDO.getDescription());
            addProductDTO.setProductName(productDO.getProductName());
            addProductDTO.setProductCode(productDO.getProductCode());
            addProductDTO.setLabel(productDO.getLabel());
            addProductDTO.setLabelName(LabelEnum.getNameByCode(productDO.getLabel()));

            addProductDTO.setMainUrl(productDO.getMainUrl());
            addProductDTO.setSequence(productDO.getSequence());
            addProductDTO.setMonthSalesNum(productDO.getMonthSalesNum());
            // 价格信息

            // 今天的日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            date = calendar.getTime();
            String day = sdf.format(date);

            List<ProductPriceDO> priceDOS = productPriceService.getPriceInfoByDay(productDO.getProductCode(),day);
            if (priceDOS !=null && priceDOS.size()>0) {
                addProductDTO.setPrice(priceDOS.get(0).getPrice());
                addProductDTO.setPriceCr(priceDOS.get(0).getPriceCr());
            }
            addProductDTOS.add(addProductDTO);
        }
        return addProductDTOS;
    }

    @Override
    public List<AddProductDTO> getProductListWXTab() {
        List<ProductDO> productDOS = list(Wrappers.<ProductDO>lambdaQuery().eq(ProductDO::getStatus,2).orderByAsc(ProductDO::getSequence).last("limit 3"));
        List<AddProductDTO> addProductDTOS = new ArrayList<>();
        for (ProductDO productDO : productDOS) {
            AddProductDTO addProductDTO = new AddProductDTO();
            addProductDTO.setDescription(productDO.getDescription());
            addProductDTO.setProductName(productDO.getProductName());
            addProductDTO.setProductCode(productDO.getProductCode());
            addProductDTO.setLabel(productDO.getLabel());
            addProductDTO.setLabelName(LabelEnum.getNameByCode(productDO.getLabel()));

            addProductDTO.setMainUrl(productDO.getMainUrl());
            addProductDTO.setSequence(productDO.getSequence());
            // 价格信息

            // 今天的日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            date = calendar.getTime();
            String day = sdf.format(date);

            List<ProductPriceDO> priceDOS = productPriceService.getPriceInfoByDay(productDO.getProductCode(),day);
            if (priceDOS !=null && priceDOS.size()>0) {
                addProductDTO.setPrice(priceDOS.get(0).getPrice());
                addProductDTO.setPriceCr(priceDOS.get(0).getPriceCr());
            }
            addProductDTOS.add(addProductDTO);
        }
        return addProductDTOS;
    }

    @Override
    public String uploadImg(MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            String realPath = "/data/tupian";

            // 重新生成文件名称
            String originalFileName = GenerateCodeUtil.createCode(12)+fileName;
            // 创建文件
            File file = new File(realPath+"/"+originalFileName);
            multipartFile.transferTo(file);
            String url = "https://www.cloudroc.top/files/"+originalFileName;
            log.info("返回的图片地址:"+url);
            return url;
        } catch (IOException e) {
            log.error("图片上传异常");
            e.printStackTrace();
        }
        return null;
    }
}
