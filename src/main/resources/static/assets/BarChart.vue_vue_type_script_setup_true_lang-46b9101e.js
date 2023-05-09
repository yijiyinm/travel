import{d as s,o as l,a as d,l as c,w as a,aj as m,k as f,n as y,Z as p}from "./index-d2e65462.js";import{E as x}from "./el-card-98c0779a.js";import{L as o,i as u}from "./index-b7e808fc.js";const h=["id"],v=s({__name:"BarChart",props:{id:{type:String,default:"barChart"},className:{type:String,default:""},width:{type:String,default:"200px",required:!0},height:{type:String,default:"200px",required:!0}},setup(e){const r=e,i={grid:{left:"2%",right:"2%",bottom:"10%",containLabel:!0},tooltip:{trigger:"axis",axisPointer:{type:"cross",crossStyle:{color:"#999"}}},legend:{x:"center",y:"bottom",data:["收入","毛利润","收入增长率","利润增长率"],textStyle:{color:"#999"}},xAxis:[{type:"category",data:["浙江","北京","上海","广东","深圳"],axisPointer:{type:"shadow"}}],yAxis:[{type:"value",min:0,max:1e4,interval:2e3,axisLabel:{formatter:"{value} "}},{type:"value",min:0,max:100,interval:20,axisLabel:{formatter:"{value}%"}}],series:[{name:"收入",type:"bar",data:[7e3,7100,7200,7300,7400],barWidth:20,itemStyle:{color:new o(0,0,0,1,[{offset:0,color:"#83bff6"},{offset:.5,color:"#188df0"},{offset:1,color:"#188df0"}])}},{name:"毛利润",type:"bar",data:[8e3,8200,8400,8600,8800],barWidth:20,itemStyle:{color:new o(0,0,0,1,[{offset:0,color:"#25d73c"},{offset:.5,color:"#1bc23d"},{offset:1,color:"#179e61"}])}},{name:"收入增长率",type:"line",yAxisIndex:1,data:[60,65,70,75,80],itemStyle:{color:"#67C23A"}},{name:"利润增长率",type:"line",yAxisIndex:1,data:[70,75,80,85,90],itemStyle:{color:"#409EFF"}}]};return l(()=>{const t=u(document.getElementById(r.id));t.setOption(i),window.addEventListener("resize",()=>{t.resize()})}),(t, g)=>{const n=x;return d(),c(n,null,{header:a(()=>[m(" 业绩柱状图 ")]),default:a(()=>[f("div",{id:e.id,class:y(e.className),style:p({height:e.height,width:e.width})},null,14,h)]),_:1})}}});export{v as _};
