import{d as J,j as ie,bd as we,S as ae,u as ue,a8 as q,b3 as ke,b as de,f as l,a as i,c as E,k as N,Y as R,aA as W,l as D,K as t,_ as pe,be as Ce,U as Se,aW as Ve,p as Ee,z as xe,n as O,e as re,aj as u,q as S,A as j,af as De,s as Ne,v as Ie,bb as L,r as T,a7 as U,o as $e,w as o,aa as se,aQ as Ae,bc as X,aK as Pe,aV as ze,P as Ue}from"./index-022b585c.js";import{E as Re,g as Be,f as Fe,_ as Je,a as Oe,b as Te,c as je,d as qe,e as Me,v as We}from"./search-46634472.js";import{E as Ye}from"./el-input-number-7718d45a.js";import{E as Ke}from"./el-dialog-9cfc5be9.js";import"./el-tooltip-d39d5b03.js";import"./el-scrollbar-52146e5d.js";import{E as G,a as Le}from"./el-form-item-4ace8013.js";import{E as Qe}from"./el-date-picker-93698a80.js";import{_ as Xe}from"./_plugin-vue_export-helper-c27b6911.js";import"./refs-790c65b0.js";const H=Symbol("elDescriptions");var M=J({name:"ElDescriptionsCell",props:{cell:{type:Object},tag:{type:String},type:{type:String}},setup(){return{descriptions:ie(H,{})}},render(){var y,g,r,I,p,h;const c=we(this.cell),{border:f,direction:s}=this.descriptions,d=s==="vertical",a=((r=(g=(y=this.cell)==null?void 0:y.children)==null?void 0:g.label)==null?void 0:r.call(g))||c.label,_=(h=(p=(I=this.cell)==null?void 0:I.children)==null?void 0:p.default)==null?void 0:h.call(p),b=c.span,A=c.align?`is-${c.align}`:"",k=c.labelAlign?`is-${c.labelAlign}`:A,V=c.className,B=c.labelClassName,x={width:ae(c.width),minWidth:ae(c.minWidth)},v=ue("descriptions");switch(this.type){case"label":return q(this.tag,{style:x,class:[v.e("cell"),v.e("label"),v.is("bordered-label",f),v.is("vertical-label",d),k,B],colSpan:d?b:1},a);case"content":return q(this.tag,{style:x,class:[v.e("cell"),v.e("content"),v.is("bordered-content",f),v.is("vertical-content",d),A,V],colSpan:d?b:b*2-1},_);default:return q("td",{style:x,class:[v.e("cell"),A],colSpan:b},[ke(a)?void 0:q("span",{class:[v.e("label"),B]},a),q("span",{class:[v.e("content"),V]},_)])}}});const Ge=de({row:{type:Array,default:()=>[]}}),He={key:1},Ze=J({name:"ElDescriptionsRow"}),et=J({...Ze,props:Ge,setup(y){const g=ie(H,{});return(r,I)=>l(g).direction==="vertical"?(i(),E(R,{key:0},[N("tr",null,[(i(!0),E(R,null,W(r.row,(p,h)=>(i(),D(l(M),{key:`tr1-${h}`,cell:p,tag:"th",type:"label"},null,8,["cell"]))),128))]),N("tr",null,[(i(!0),E(R,null,W(r.row,(p,h)=>(i(),D(l(M),{key:`tr2-${h}`,cell:p,tag:"td",type:"content"},null,8,["cell"]))),128))])],64)):(i(),E("tr",He,[(i(!0),E(R,null,W(r.row,(p,h)=>(i(),E(R,{key:`tr3-${h}`},[l(g).border?(i(),E(R,{key:0},[t(l(M),{cell:p,tag:"td",type:"label"},null,8,["cell"]),t(l(M),{cell:p,tag:"td",type:"content"},null,8,["cell"])],64)):(i(),D(l(M),{key:1,cell:p,tag:"td",type:"both"},null,8,["cell"]))],64))),128))]))}});var tt=pe(et,[["__file","/home/runner/work/element-plus/element-plus/packages/components/descriptions/src/descriptions-row.vue"]]);const lt=de({border:{type:Boolean,default:!1},column:{type:Number,default:3},direction:{type:String,values:["horizontal","vertical"],default:"horizontal"},size:Ce,title:{type:String,default:""},extra:{type:String,default:""}}),ot=J({name:"ElDescriptions"}),nt=J({...ot,props:lt,setup(y){const g=y,r=ue("descriptions"),I=Se(),p=Ve();Ee(H,g);const h=xe(()=>[r.b(),r.m(I.value)]),c=(s,d,a,_=!1)=>(s.props||(s.props={}),d>a&&(s.props.span=a),_&&(s.props.span=d),s),f=()=>{var s;const d=De((s=p.default)==null?void 0:s.call(p)).filter(k=>{var V;return((V=k==null?void 0:k.type)==null?void 0:V.name)==="ElDescriptionsItem"}),a=[];let _=[],b=g.column,A=0;return d.forEach((k,V)=>{var B;const x=((B=k.props)==null?void 0:B.span)||1;if(V<d.length-1&&(A+=x>b?b:x),V===d.length-1){const v=g.column-A%g.column;_.push(c(k,v,b,!0)),a.push(_);return}x<b?(b-=x,_.push(k)):(_.push(c(k,x,b)),a.push(_),b=g.column,_=[])}),a};return(s,d)=>(i(),E("div",{class:O(l(h))},[s.title||s.extra||s.$slots.title||s.$slots.extra?(i(),E("div",{key:0,class:O(l(r).e("header"))},[N("div",{class:O(l(r).e("title"))},[re(s.$slots,"title",{},()=>[u(S(s.title),1)])],2),N("div",{class:O(l(r).e("extra"))},[re(s.$slots,"extra",{},()=>[u(S(s.extra),1)])],2)],2)):j("v-if",!0),N("div",{class:O(l(r).e("body"))},[N("table",{class:O([l(r).e("table"),l(r).is("bordered",s.border)])},[N("tbody",null,[(i(!0),E(R,null,W(f(),(a,_)=>(i(),D(tt,{key:_,row:a},null,8,["row"]))),128))])],2)],2)],2))}});var at=pe(nt,[["__file","/home/runner/work/element-plus/element-plus/packages/components/descriptions/src/description.vue"]]),ce=J({name:"ElDescriptionsItem",props:{label:{type:String,default:""},span:{type:Number,default:1},width:{type:[String,Number],default:""},minWidth:{type:[String,Number],default:""},align:{type:String,default:"left"},labelAlign:{type:String,default:""},className:{type:String,default:""},labelClassName:{type:String,default:""}}});const rt=Ne(at,{DescriptionsItem:ce}),st=Ie(ce);function it(y){return L({url:"/order/getOrderList",method:"post",data:y})}function ut(y){return L({url:"/order/orderRefund",method:"post",data:y})}function dt(y){return L({url:"/order/getOrderDetailWX",method:"post",data:y})}function pt(y){return L({url:"/order/fsxJsByOrderCode",method:"post",data:y})}const ct={class:"app-container"},mt={class:"search"},ft=["src"],_t={style:{"margin-bottom":"20px"}},bt={class:"dialog-footer"},yt={name:"order"},gt=J({...yt,setup(y){const g=T(G);let r=U({}),I=U({});const p=T(G),h=U({visible:!1}),c=T(!1),f=T(),s=T(0),d=U({visible:!1}),a=U({current:1,size:10}),_=T(),b={1:"待支付",2:"支付成功",3:"支付失败",4:"已全部退款",5:"部分退款",6:"退款申请中",7:"退款失败",8:"超时取消",9:"已经出行"};function A(m){const n={"1,5,6":"warning","2,4,9":"success","3,7":"error",8:"info"};for(const $ in n)if($.includes(m))return n[$]}function k(){c.value=!0,it(a).then(({data:m})=>{_.value=m==null?void 0:m.records,s.value=m==null?void 0:m.total}).finally(()=>{c.value=!1})}function V(){g.value.resetFields(),a.current=1,k()}const B=(m,n,$)=>"￥"+$;function x({payPrice:m,refundAmount:n,orderCode:$}){I.money=(m*100-n*100)/100,h.visible=!0,r.orderCode=$,r.refundAmount=null}function v(){if(r.refundAmount&&r.refundAmount>I.money){X.error("退款不能超过剩余金额");return}p.value.validate(m=>{m&&ut({orderCode:r.orderCode,refundAmount:r.refundAmount}).then(()=>{X.success("操作成功"),r=U({}),Y(),V()})})}function me(m){f.value={},d.visible=!0,d.title="详情",dt({orderCode:m}).then(n=>{f.value=n==null?void 0:n.data})}function Y(){d.visible=!1,h.visible=!1,I=U({}),r=U({}),p.value.clearValidate()}$e(()=>{k()});function fe({orderCode:m}){Pe.confirm("确认结算该分销商订单吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(()=>{pt({orderCode:m}).then(()=>{X.success("结算成功"),V()}).finally()})}return(m,n)=>{const $=ze,P=Le,Q=Be,Z=Fe,_e=Qe,be=Je,F=Ue,ye=Oe,ee=G,w=Te,K=je,te=qe,ge=Me,he=Re,z=st,le=rt,oe=Ke,ve=Ye,ne=We;return i(),E("div",ct,[N("div",mt,[t(ee,{ref_key:"queryFormRef",ref:g,model:l(a),inline:!0},{default:o(()=>[t(P,{prop:"orderCode",label:"订单编号"},{default:o(()=>[t($,{modelValue:l(a).orderCode,"onUpdate:modelValue":n[0]||(n[0]=e=>l(a).orderCode=e),placeholder:"请输入",clearable:""},null,8,["modelValue"])]),_:1}),t(P,{label:"订单状态",prop:"orderStatus"},{default:o(()=>[t(Z,{modelValue:l(a).orderStatus,"onUpdate:modelValue":n[1]||(n[1]=e=>l(a).orderStatus=e),placeholder:"订单状态",clearable:""},{default:o(()=>[(i(),E(R,null,W(b,(e,C)=>t(Q,{key:C,label:e,value:C},null,8,["label","value"])),64))]),_:1},8,["modelValue"])]),_:1}),t(P,{label:"是否分销商",prop:"distributionIs"},{default:o(()=>[t(Z,{modelValue:l(a).distributionIs,"onUpdate:modelValue":n[2]||(n[2]=e=>l(a).distributionIs=e),placeholder:"是否分销商",clearable:""},{default:o(()=>[t(Q,{value:!0,label:"是"}),t(Q,{value:!1,label:"否"})]),_:1},8,["modelValue"])]),_:1}),t(P,{prop:"fxsCode",label:"分销商编码"},{default:o(()=>[t($,{modelValue:l(a).fxsCode,"onUpdate:modelValue":n[3]||(n[3]=e=>l(a).fxsCode=e),placeholder:"请输入",clearable:""},null,8,["modelValue"])]),_:1}),t(P,{prop:"fxsPhone",label:"分销商手机号"},{default:o(()=>[t($,{modelValue:l(a).fxsPhone,"onUpdate:modelValue":n[4]||(n[4]=e=>l(a).fxsPhone=e),placeholder:"请输入",clearable:""},null,8,["modelValue"])]),_:1}),t(P,{prop:"month",label:"日期"},{default:o(()=>[t(_e,{modelValue:l(a).month,"onUpdate:modelValue":n[5]||(n[5]=e=>l(a).month=e),type:"month",placeholder:"请选择","value-format":"YYYY-MM"},null,8,["modelValue"])]),_:1}),t(P,null,{default:o(()=>[t(F,{type:"primary",onClick:k},{default:o(()=>[t(be),u("搜索")]),_:1}),t(F,{onClick:V},{default:o(()=>[t(ye),u("重置")]),_:1})]),_:1})]),_:1},8,["model"])]),t(he,{shadow:"never"},{default:o(()=>[se((i(),D(te,{ref:"dataTableRef",data:l(_),"highlight-current-row":"",selectable:"false",border:""},{default:o(()=>[t(w,{label:"订单编码",prop:"orderCode",width:"150"}),t(w,{label:"商品名称","min-width":"100","max-width":"200"},{default:o(e=>[N("img",{src:e.row.mainUrl},null,8,ft),u(" "+S(e.row.productName),1)]),_:1}),t(w,{label:"状态",width:"100"},{default:o(e=>[t(K,{type:A(e.row.orderStatus)},{default:o(()=>[u(S(b[e.row.orderStatus]),1)]),_:2},1032,["type"])]),_:1}),t(w,{label:"出行时间",prop:"chuXingDate","min-width":"100"}),t(w,{label:"数量",prop:"num","min-width":"100"}),t(w,{label:"支付金额",prop:"payPrice","min-width":"100",formatter:B}),t(w,{label:"退款金额",prop:"refundAmount","min-width":"100"}),t(w,{label:"下单时间",prop:"createDate","min-width":"100"}),t(w,{label:"分销商编码",prop:"fxsCode","min-width":"100"}),t(w,{label:"分销商手机号",prop:"fxsPhone","min-width":"100"}),t(w,{label:"分销商结算",prop:"fxsJsName","min-width":"100"},{default:o(e=>[e.row.fxsJs==1?(i(),D(K,{key:0,type:"success"},{default:o(()=>[u(S(e.row.fxsJsName),1)]),_:2},1024)):j("",!0),e.row.fxsJs==2?(i(),D(K,{key:1,type:"warning"},{default:o(()=>[u(S(e.row.fxsJsName),1)]),_:2},1024)):j("",!0)]),_:1}),t(w,{fixed:"right",label:"操作",width:"220"},{default:o(e=>[t(F,{type:"primary",size:"small",link:"",onClick:C=>me(e.row.orderCode)},{default:o(()=>[u(" 详情 ")]),_:2},1032,["onClick"]),"2,5,7".includes(e.row.orderStatus)?(i(),D(F,{key:0,type:"primary",size:"small",link:"",onClick:C=>x(e.row)},{default:o(()=>[u(" 退款 ")]),_:2},1032,["onClick"])):j("",!0),e.row.fxsJs==2?(i(),D(F,{key:1,type:"primary",size:"small",link:"",onClick:C=>fe(e.row)},{default:o(()=>[u(" 分销商结算 ")]),_:2},1032,["onClick"])):j("",!0)]),_:1})]),_:1},8,["data"])),[[ne,l(c)]]),l(s)>0?(i(),D(ge,{key:0,total:l(s),"onUpdate:total":n[6]||(n[6]=e=>Ae(s)?s.value=e:null),page:l(a).current,"onUpdate:page":n[7]||(n[7]=e=>l(a).current=e),limit:l(a).size,"onUpdate:limit":n[8]||(n[8]=e=>l(a).size=e),onPagination:k},null,8,["total","page","limit"])):j("",!0)]),_:1}),t(oe,{title:l(d).title,modelValue:l(d).visible,"onUpdate:modelValue":n[9]||(n[9]=e=>l(d).visible=e),width:"1000px",onClose:Y},{default:o(()=>[t(le,{title:"订单详情",column:3},{default:o(()=>[t(z,{label:"订单状态"},{default:o(()=>{var e;return[t(K,{type:A((e=l(f))==null?void 0:e.orderStatus)},{default:o(()=>{var C;return[u(S(b[(C=l(f))==null?void 0:C.orderStatus]),1)]}),_:1},8,["type"])]}),_:1}),t(z,{label:"产品数量"},{default:o(()=>{var e;return[u(S((e=l(f))==null?void 0:e.num),1)]}),_:1}),t(z,{label:"订单金额"},{default:o(()=>{var e;return[u(S((e=l(f))==null?void 0:e.payPrice),1)]}),_:1}),t(z,{label:"退款金额"},{default:o(()=>{var e;return[u(S(((e=l(f))==null?void 0:e.refundAmount)||"-"),1)]}),_:1}),t(z,{label:"商品Code"},{default:o(()=>{var e,C;return[u(S((C=(e=l(f))==null?void 0:e.productInfo)==null?void 0:C.productCode),1)]}),_:1}),t(z,{label:"商品名称"},{default:o(()=>{var e,C;return[u(S((C=(e=l(f))==null?void 0:e.productInfo)==null?void 0:C.productName),1)]}),_:1}),t(z,{label:"出行时间"},{default:o(()=>{var e;return[u(S((e=l(f))==null?void 0:e.chuXingDate),1)]}),_:1})]),_:1}),t(le,{title:"出行人信息"},{default:o(()=>[t(z,{label:"出行人信息",span:4},{default:o(()=>{var e;return[se((i(),D(te,{ref:"dataTableRef",data:(e=l(f))==null?void 0:e.touristInfo,"highlight-current-row":"",selectable:"false"},{default:o(()=>[t(w,{label:"姓名",prop:"name"}),t(w,{label:"手机号",prop:"phone"}),t(w,{label:"身份证号",prop:"cardId"})]),_:1},8,["data"])),[[ne,l(c)]])]}),_:1})]),_:1})]),_:1},8,["title","modelValue"]),t(oe,{title:"退款",modelValue:l(h).visible,"onUpdate:modelValue":n[11]||(n[11]=e=>l(h).visible=e),width:"350px",onClose:Y},{footer:o(()=>[N("div",bt,[t(F,{type:"primary",onClick:v},{default:o(()=>[u("确定")]),_:1}),t(F,{onClick:Y},{default:o(()=>[u("取消")]),_:1})])]),default:o(()=>[t(ee,{model:l(r),ref_key:"refundFormRef",ref:p,"label-width":"80px","label-position":"top"},{default:o(()=>[N("div",_t,"剩余金额:"+S(l(I).money),1),t(P,{label:"退款金额",prop:"refundAmount",rules:[{required:!0,message:"请输入",trigger:"blur"}]},{default:o(()=>[t(ve,{modelValue:l(r).refundAmount,"onUpdate:modelValue":n[10]||(n[10]=e=>l(r).refundAmount=e),"controls-position":"right",min:0,placeholder:"请输入",style:{width:"230px"}},null,8,["modelValue"])]),_:1})]),_:1},8,["model"])]),_:1},8,["modelValue"])])}}});const Nt=Xe(gt,[["__scopeId","data-v-7c969807"]]);export{Nt as default};