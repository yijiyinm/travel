import{d as F,j as ue,bd as Ee,S as re,u as de,a8 as M,b3 as De,b as pe,f as l,a as s,c as S,k as I,Y as z,aA as q,l as x,K as t,_ as ce,be as Ne,U as Ie,aW as $e,p as Ae,z as Pe,n as T,e as se,aj as u,q as k,A as j,af as Ue,s as ze,v as Je,bb as Q,r as O,a7 as B,o as Re,w as o,aa as ie,aQ as Be,bc as L,aK as Oe,aV as Fe,P as Te}from"./index-d824f41b.js";import{E as je,g as qe,f as Me,_ as We,a as Ye,b as Ke,c as Le,d as Qe,e as Xe,v as Ge}from"./search-20a724e0.js";import{E as He}from"./el-input-number-7b12297b.js";import{E as Ze}from"./el-dialog-0cbdfc40.js";import"./el-tooltip-14bb6b3d.js";import"./el-scrollbar-d3ac4d06.js";import{E as et,_ as tt}from"./el-date-picker-adeadc64.js";import{E as H,a as lt}from"./el-form-item-4f1e53a0.js";import{_ as ot}from"./_plugin-vue_export-helper-c27b6911.js";import"./refs-528003e8.js";const Z=Symbol("elDescriptions");var W=F({name:"ElDescriptionsCell",props:{cell:{type:Object},tag:{type:String},type:{type:String}},setup(){return{descriptions:ue(Z,{})}},render(){var y,h,d,_,p,C;const m=Ee(this.cell),{border:P,direction:r}=this.descriptions,f=r==="vertical",c=((d=(h=(y=this.cell)==null?void 0:y.children)==null?void 0:h.label)==null?void 0:d.call(h))||m.label,a=(C=(p=(_=this.cell)==null?void 0:_.children)==null?void 0:p.default)==null?void 0:C.call(p),v=m.span,$=m.align?`is-${m.align}`:"",E=m.labelAlign?`is-${m.labelAlign}`:$,D=m.className,V=m.labelClassName,N={width:re(m.width),minWidth:re(m.minWidth)},w=de("descriptions");switch(this.type){case"label":return M(this.tag,{style:N,class:[w.e("cell"),w.e("label"),w.is("bordered-label",P),w.is("vertical-label",f),E,V],colSpan:f?v:1},c);case"content":return M(this.tag,{style:N,class:[w.e("cell"),w.e("content"),w.is("bordered-content",P),w.is("vertical-content",f),$,D],colSpan:f?v:v*2-1},a);default:return M("td",{style:N,class:[w.e("cell"),$],colSpan:v},[De(c)?void 0:M("span",{class:[w.e("label"),V]},c),M("span",{class:[w.e("content"),D]},a)])}}});const nt=pe({row:{type:Array,default:()=>[]}}),at={key:1},rt=F({name:"ElDescriptionsRow"}),st=F({...rt,props:nt,setup(y){const h=ue(Z,{});return(d,_)=>l(h).direction==="vertical"?(s(),S(z,{key:0},[I("tr",null,[(s(!0),S(z,null,q(d.row,(p,C)=>(s(),x(l(W),{key:`tr1-${C}`,cell:p,tag:"th",type:"label"},null,8,["cell"]))),128))]),I("tr",null,[(s(!0),S(z,null,q(d.row,(p,C)=>(s(),x(l(W),{key:`tr2-${C}`,cell:p,tag:"td",type:"content"},null,8,["cell"]))),128))])],64)):(s(),S("tr",at,[(s(!0),S(z,null,q(d.row,(p,C)=>(s(),S(z,{key:`tr3-${C}`},[l(h).border?(s(),S(z,{key:0},[t(l(W),{cell:p,tag:"td",type:"label"},null,8,["cell"]),t(l(W),{cell:p,tag:"td",type:"content"},null,8,["cell"])],64)):(s(),x(l(W),{key:1,cell:p,tag:"td",type:"both"},null,8,["cell"]))],64))),128))]))}});var it=ce(st,[["__file","/home/runner/work/element-plus/element-plus/packages/components/descriptions/src/descriptions-row.vue"]]);const ut=pe({border:{type:Boolean,default:!1},column:{type:Number,default:3},direction:{type:String,values:["horizontal","vertical"],default:"horizontal"},size:Ne,title:{type:String,default:""},extra:{type:String,default:""}}),dt=F({name:"ElDescriptions"}),pt=F({...dt,props:ut,setup(y){const h=y,d=de("descriptions"),_=Ie(),p=$e();Ae(Z,h);const C=Pe(()=>[d.b(),d.m(_.value)]),m=(r,f,c,a=!1)=>(r.props||(r.props={}),f>c&&(r.props.span=c),a&&(r.props.span=f),r),P=()=>{var r;const f=Ue((r=p.default)==null?void 0:r.call(p)).filter(E=>{var D;return((D=E==null?void 0:E.type)==null?void 0:D.name)==="ElDescriptionsItem"}),c=[];let a=[],v=h.column,$=0;return f.forEach((E,D)=>{var V;const N=((V=E.props)==null?void 0:V.span)||1;if(D<f.length-1&&($+=N>v?v:N),D===f.length-1){const w=h.column-$%h.column;a.push(m(E,w,v,!0)),c.push(a);return}N<v?(v-=N,a.push(E)):(a.push(m(E,N,v)),c.push(a),v=h.column,a=[])}),c};return(r,f)=>(s(),S("div",{class:T(l(C))},[r.title||r.extra||r.$slots.title||r.$slots.extra?(s(),S("div",{key:0,class:T(l(d).e("header"))},[I("div",{class:T(l(d).e("title"))},[se(r.$slots,"title",{},()=>[u(k(r.title),1)])],2),I("div",{class:T(l(d).e("extra"))},[se(r.$slots,"extra",{},()=>[u(k(r.extra),1)])],2)],2)):j("v-if",!0),I("div",{class:T(l(d).e("body"))},[I("table",{class:T([l(d).e("table"),l(d).is("bordered",r.border)])},[I("tbody",null,[(s(!0),S(z,null,q(P(),(c,a)=>(s(),x(it,{key:a,row:c},null,8,["row"]))),128))])],2)],2)],2))}});var ct=ce(pt,[["__file","/home/runner/work/element-plus/element-plus/packages/components/descriptions/src/description.vue"]]),me=F({name:"ElDescriptionsItem",props:{label:{type:String,default:""},span:{type:Number,default:1},width:{type:[String,Number],default:""},minWidth:{type:[String,Number],default:""},align:{type:String,default:"left"},labelAlign:{type:String,default:""},className:{type:String,default:""},labelClassName:{type:String,default:""}}});const mt=ze(ct,{DescriptionsItem:me}),ft=Je(me);function _t(y){return Q({url:"/order/getOrderList",method:"post",data:y})}function bt(y){return Q({url:"/order/orderRefund",method:"post",data:y})}function gt(y){return Q({url:"/order/getOrderDetailWX",method:"post",data:y})}function yt(y){return Q({url:"/order/fsxJsByOrderCode",method:"post",data:y})}const ht={class:"app-container"},vt={class:"search"},wt=["src"],Ct={style:{"margin-bottom":"20px"}},kt={class:"dialog-footer"},St={name:"order"},Vt=F({...St,setup(y){const h=O(H),d=O([]);let _=B({}),p=B({});const C=O(H),m=B({visible:!1}),P=O(!1),r=O(),f=O(0),c=B({visible:!1}),a=B({current:1,size:10}),v=O(),$={1:"待支付",2:"支付成功",3:"支付失败",4:"已全部退款",5:"部分退款",6:"退款申请中",7:"退款失败",8:"超时取消",9:"已经出行"},E={1:"已结算",2:"未结算"};function D(i){const n={"1,5,6":"warning","2,4,9":"success","3,7":"error",8:"info"};for(const A in n)if(A.includes(i))return n[A]}function V(){P.value=!0,_t(a).then(({data:i})=>{v.value=i==null?void 0:i.records,f.value=i==null?void 0:i.total}).finally(()=>{P.value=!1})}function N(){h.value.resetFields(),a.current=1,V()}const w=(i,n,A)=>"￥"+A;function fe({payPrice:i,refundAmount:n,orderCode:A}){p.money=(i*100-n*100)/100,m.visible=!0,_.orderCode=A,_.refundAmount=null}function _e(){if(_.refundAmount&&_.refundAmount>p.money){L.error("退款不能超过剩余金额");return}C.value.validate(i=>{i&&bt({orderCode:_.orderCode,refundAmount:_.refundAmount}).then(()=>{L.success("操作成功"),_=B({}),X(),V()})})}function be(i){r.value={},c.visible=!0,c.title="详情",gt({orderCode:i}).then(n=>{r.value=n==null?void 0:n.data})}function ge(){c.visible=!1}function X(){m.visible=!1,p=B({}),_=B({}),C.value.clearValidate()}Re(()=>{V()});function ye(i){d.value=i.map(n=>n.orderCode)}function ee(i){const n=[i||d.value].join(",");if(!n){L.warning("请勾选分销商订单");return}Oe.confirm("确认结算已选中分销商订单吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(()=>{yt({orderCodes:n}).then(()=>{L.success("结算成功"),V()}).finally()})}function he(i){return i.fxsJs==2}return(i,n)=>{const A=Fe,U=lt,Y=qe,G=Me,ve=et,we=We,J=Te,Ce=Ye,te=H,ke=tt,b=Ke,K=Le,le=Qe,Se=Xe,Ve=je,R=ft,oe=mt,ne=Ze,xe=He,ae=Ge;return s(),S("div",ht,[I("div",vt,[t(te,{ref_key:"queryFormRef",ref:h,model:l(a),inline:!0},{default:o(()=>[t(U,{prop:"orderCode",label:"订单编号"},{default:o(()=>[t(A,{modelValue:l(a).orderCode,"onUpdate:modelValue":n[0]||(n[0]=e=>l(a).orderCode=e),placeholder:"请输入",clearable:""},null,8,["modelValue"])]),_:1}),t(U,{label:"订单状态",prop:"orderStatus"},{default:o(()=>[t(G,{modelValue:l(a).orderStatus,"onUpdate:modelValue":n[1]||(n[1]=e=>l(a).orderStatus=e),placeholder:"订单状态",clearable:""},{default:o(()=>[(s(),S(z,null,q($,(e,g)=>t(Y,{key:g,label:e,value:g},null,8,["label","value"])),64))]),_:1},8,["modelValue"])]),_:1}),t(U,{label:"是否分销商",prop:"distributionIs"},{default:o(()=>[t(G,{modelValue:l(a).distributionIs,"onUpdate:modelValue":n[2]||(n[2]=e=>l(a).distributionIs=e),placeholder:"是否分销商",clearable:""},{default:o(()=>[t(Y,{value:!0,label:"是"}),t(Y,{value:!1,label:"否"})]),_:1},8,["modelValue"])]),_:1}),t(U,{prop:"fxsCode",label:"分销商编码"},{default:o(()=>[t(A,{modelValue:l(a).fxsCode,"onUpdate:modelValue":n[3]||(n[3]=e=>l(a).fxsCode=e),placeholder:"请输入",clearable:""},null,8,["modelValue"])]),_:1}),t(U,{prop:"fxsPhone",label:"分销商手机号"},{default:o(()=>[t(A,{modelValue:l(a).fxsPhone,"onUpdate:modelValue":n[4]||(n[4]=e=>l(a).fxsPhone=e),placeholder:"请输入",clearable:""},null,8,["modelValue"])]),_:1}),t(U,{prop:"month",label:"日期"},{default:o(()=>[t(ve,{modelValue:l(a).month,"onUpdate:modelValue":n[5]||(n[5]=e=>l(a).month=e),type:"month",placeholder:"请选择","value-format":"YYYY-MM",style:{width:"100%"}},null,8,["modelValue"])]),_:1}),t(U,{label:"分销商是否结算",prop:"fxsJs"},{default:o(()=>[t(G,{modelValue:l(a).fxsJs,"onUpdate:modelValue":n[6]||(n[6]=e=>l(a).fxsJs=e),placeholder:"请选择",clearable:""},{default:o(()=>[(s(!0),S(z,null,q(Object.entries(E),([e,g])=>(s(),x(Y,{key:e,label:g,value:e},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1}),t(U,null,{default:o(()=>[t(J,{type:"primary",onClick:V},{default:o(()=>[t(we),u("搜索")]),_:1}),t(J,{onClick:N},{default:o(()=>[t(Ce),u("重置")]),_:1})]),_:1})]),_:1},8,["model"])]),t(Ve,{shadow:"never"},{header:o(()=>[t(J,{type:"warning",disabled:l(d).length===0,onClick:n[7]||(n[7]=e=>ee())},{default:o(()=>[t(ke),u("分销商批量结算")]),_:1},8,["disabled"])]),default:o(()=>[ie((s(),x(le,{ref:"dataTableRef",data:l(v),"highlight-current-row":"",selectable:"false",onSelectionChange:ye,border:""},{default:o(()=>[t(b,{type:"selection",width:"55",align:"center",selectable:he}),t(b,{label:"订单编码",prop:"orderCode",width:"150"}),t(b,{label:"商品名称","min-width":"100","max-width":"200"},{default:o(e=>[I("img",{src:e.row.mainUrl},null,8,wt),u(" "+k(e.row.productName),1)]),_:1}),t(b,{label:"状态",width:"100"},{default:o(e=>[t(K,{type:D(e.row.orderStatus)},{default:o(()=>[u(k($[e.row.orderStatus]),1)]),_:2},1032,["type"])]),_:1}),t(b,{label:"出行时间",prop:"chuXingDate","min-width":"100"}),t(b,{label:"数量",prop:"num","min-width":"100"}),t(b,{label:"支付金额",prop:"payPrice","min-width":"100",formatter:w}),t(b,{label:"退款金额",prop:"refundAmount","min-width":"100"}),t(b,{label:"下单时间",prop:"createDate","min-width":"100"}),t(b,{label:"分销商编码",prop:"fxsCode","min-width":"100"}),t(b,{label:"分销商手机号",prop:"fxsPhone","min-width":"100"}),t(b,{label:"分销商结算",prop:"fxsJsName","min-width":"100"},{default:o(e=>[e.row.fxsJs==1?(s(),x(K,{key:0,type:"success"},{default:o(()=>[u(k(e.row.fxsJsName),1)]),_:2},1024)):j("",!0),e.row.fxsJs==2?(s(),x(K,{key:1,type:"warning"},{default:o(()=>[u(k(e.row.fxsJsName),1)]),_:2},1024)):j("",!0)]),_:1}),t(b,{fixed:"right",label:"操作",width:"220"},{default:o(e=>[t(J,{type:"primary",size:"small",link:"",onClick:g=>be(e.row.orderCode)},{default:o(()=>[u(" 详情 ")]),_:2},1032,["onClick"]),"2,5,7".includes(e.row.orderStatus)?(s(),x(J,{key:0,type:"primary",size:"small",link:"",onClick:g=>fe(e.row)},{default:o(()=>[u(" 退款 ")]),_:2},1032,["onClick"])):j("",!0),e.row.fxsJs==2?(s(),x(J,{key:1,type:"primary",size:"small",link:"",onClick:g=>ee(e.row.orderCode)},{default:o(()=>[u(" 分销商结算 ")]),_:2},1032,["onClick"])):j("",!0)]),_:1})]),_:1},8,["data"])),[[ae,l(P)]]),l(f)>0?(s(),x(Se,{key:0,total:l(f),"onUpdate:total":n[8]||(n[8]=e=>Be(f)?f.value=e:null),page:l(a).current,"onUpdate:page":n[9]||(n[9]=e=>l(a).current=e),limit:l(a).size,"onUpdate:limit":n[10]||(n[10]=e=>l(a).size=e),onPagination:V},null,8,["total","page","limit"])):j("",!0)]),_:1}),t(ne,{title:l(c).title,modelValue:l(c).visible,"onUpdate:modelValue":n[11]||(n[11]=e=>l(c).visible=e),width:"1000px",onClose:ge},{default:o(()=>[t(oe,{title:"订单详情",column:3},{default:o(()=>[t(R,{label:"订单状态"},{default:o(()=>{var e;return[t(K,{type:D((e=l(r))==null?void 0:e.orderStatus)},{default:o(()=>{var g;return[u(k($[(g=l(r))==null?void 0:g.orderStatus]),1)]}),_:1},8,["type"])]}),_:1}),t(R,{label:"产品数量"},{default:o(()=>{var e;return[u(k((e=l(r))==null?void 0:e.num),1)]}),_:1}),t(R,{label:"订单金额"},{default:o(()=>{var e;return[u(k((e=l(r))==null?void 0:e.payPrice),1)]}),_:1}),t(R,{label:"退款金额"},{default:o(()=>{var e;return[u(k(((e=l(r))==null?void 0:e.refundAmount)||"-"),1)]}),_:1}),t(R,{label:"商品Code"},{default:o(()=>{var e,g;return[u(k((g=(e=l(r))==null?void 0:e.productInfo)==null?void 0:g.productCode),1)]}),_:1}),t(R,{label:"商品名称"},{default:o(()=>{var e,g;return[u(k((g=(e=l(r))==null?void 0:e.productInfo)==null?void 0:g.productName),1)]}),_:1}),t(R,{label:"出行时间"},{default:o(()=>{var e;return[u(k((e=l(r))==null?void 0:e.chuXingDate),1)]}),_:1})]),_:1}),t(oe,{title:"出行人信息"},{default:o(()=>[t(R,{label:"出行人信息",span:4},{default:o(()=>{var e;return[ie((s(),x(le,{ref:"dataTableRef",data:(e=l(r))==null?void 0:e.touristInfo,"highlight-current-row":"",selectable:"false"},{default:o(()=>[t(b,{label:"姓名",prop:"name"}),t(b,{label:"手机号",prop:"phone"}),t(b,{label:"身份证号",prop:"cardId"})]),_:1},8,["data"])),[[ae,l(P)]])]}),_:1})]),_:1})]),_:1},8,["title","modelValue"]),t(ne,{title:"退款",modelValue:l(m).visible,"onUpdate:modelValue":n[13]||(n[13]=e=>l(m).visible=e),width:"350px",onClose:X},{footer:o(()=>[I("div",kt,[t(J,{type:"primary",onClick:_e},{default:o(()=>[u("确定")]),_:1}),t(J,{onClick:X},{default:o(()=>[u("取消")]),_:1})])]),default:o(()=>[t(te,{model:l(_),ref_key:"refundFormRef",ref:C,"label-width":"80px","label-position":"top"},{default:o(()=>[I("div",Ct,"剩余金额:"+k(l(p).money),1),t(U,{label:"退款金额",prop:"refundAmount",rules:[{required:!0,message:"请输入",trigger:"blur"}]},{default:o(()=>[t(xe,{modelValue:l(_).refundAmount,"onUpdate:modelValue":n[12]||(n[12]=e=>l(_).refundAmount=e),"controls-position":"right",min:0,placeholder:"请输入",style:{width:"230px"}},null,8,["modelValue"])]),_:1})]),_:1},8,["model"])]),_:1},8,["modelValue"])])}}});const Jt=ot(Vt,[["__scopeId","data-v-f3b8cda9"]]);export{Jt as default};