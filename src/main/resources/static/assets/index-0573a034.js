import{d as j,r as c,a7 as f,o as O,a as b,c as G,k as I,K as e,w as o,f as l,aj as p,aa as H,l as v,A as w,aQ as J,aK as W,bc as V,P as X}from"./index-d824f41b.js";import{E as Y,g as Z,f as ee,_ as te,a as oe,b as le,d as ne,e as ae,v as se}from"./search-20a724e0.js";import{E as ie}from"./el-dialog-0cbdfc40.js";import{E as re}from"./el-input-number-7b12297b.js";import"./el-tooltip-14bb6b3d.js";import"./el-scrollbar-d3ac4d06.js";import{E as D,a as ue}from"./el-form-item-4f1e53a0.js";import{g as de,d as pe,u as _e}from"./index-9b08b1ec.js";import{_ as me}from"./_plugin-vue_export-helper-c27b6911.js";import"./refs-528003e8.js";const ce={class:"app-container"},fe={class:"search"},be={class:"dialog-footer"},ye={name:"distribution"},ve=j({...ye,setup(xe){const k=c(D);let i=f({});const x=c(D),y=f({visible:!1}),_=c(!1),m=c(0),B=f({fxsSetDay:[{required:!0,message:"请输入",trigger:"blur"}]}),s=f({current:1,size:10}),C=c();function u(){_.value=!0,de(s).then(({data:a})=>{C.value=a==null?void 0:a.records,m.value=a==null?void 0:a.total}).finally(()=>{_.value=!1})}function F({openId:a}){W.confirm("确认作废该分销商吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(()=>{_.value=!0,pe({openId:a}).then(()=>{V.success("作废成功"),u()}).finally(()=>_.value=!1)})}function U({fxsSetDay:a,openId:n}){y.visible=!0,i.fxsSetDay=a,i.openId=n}function R(){x.value.validate(a=>{a&&_e({fxsSetDay:i.fxsSetDay,openId:i.openId}).then(()=>{V.success("操作成功"),g(),u()})})}function g(){y.visible=!1,x.value.clearValidate(),i=f({})}function T(){k.value.resetFields(),s.current=1,u()}return O(()=>{u()}),(a,n)=>{const E=Z,z=ee,h=ue,N=te,d=X,q=oe,S=D,r=le,L=ne,M=ae,P=Y,Q=re,A=ie,K=se;return b(),G("div",ce,[I("div",fe,[e(S,{ref_key:"queryFormRef",ref:k,model:l(s),inline:!0},{default:o(()=>[e(h,{label:"是否分销商",prop:"distributionIs"},{default:o(()=>[e(z,{modelValue:l(s).distributionIs,"onUpdate:modelValue":n[0]||(n[0]=t=>l(s).distributionIs=t),placeholder:"是否分销商",clearable:""},{default:o(()=>[e(E,{value:!0,label:"是"}),e(E,{value:!1,label:"否"})]),_:1},8,["modelValue"])]),_:1}),e(h,null,{default:o(()=>[e(d,{type:"primary",onClick:u},{default:o(()=>[e(N),p("搜索")]),_:1}),e(d,{onClick:T},{default:o(()=>[e(q),p("重置")]),_:1})]),_:1})]),_:1},8,["model"])]),e(P,{shadow:"never"},{default:o(()=>[H((b(),v(L,{ref:"dataTableRef",data:l(C),"highlight-current-row":"",selectable:"false",border:""},{default:o(()=>[e(r,{label:"用户名",prop:"name",width:"150"}),e(r,{label:"openId",prop:"openId","min-width":"100"}),e(r,{label:"手机号",prop:"phone","min-width":"100"}),e(r,{label:"创建时间",prop:"createDate","min-width":"100"}),e(r,{label:"分销商编码",prop:"fxsCode","min-width":"100"}),e(r,{label:"分享天数",prop:"fxsSetDay","min-width":"100"}),e(r,{label:"操作",width:"220"},{default:o(t=>[t.row.fxsCode?(b(),v(d,{key:0,type:"primary",size:"small",link:"",onClick:$=>F({openId:t.row.openId})},{default:o(()=>[p(" 作废 ")]),_:2},1032,["onClick"])):w("",!0),t.row.fxsCode?(b(),v(d,{key:1,type:"primary",size:"small",link:"",onClick:$=>U({openId:t.row.openId,fxsSetDay:t.row.fxsSetDay})},{default:o(()=>[p(" 分享天数 ")]),_:2},1032,["onClick"])):w("",!0)]),_:1})]),_:1},8,["data"])),[[K,l(_)]]),l(m)>0?(b(),v(M,{key:0,total:l(m),"onUpdate:total":n[1]||(n[1]=t=>J(m)?m.value=t:null),page:l(s).current,"onUpdate:page":n[2]||(n[2]=t=>l(s).current=t),limit:l(s).size,"onUpdate:limit":n[3]||(n[3]=t=>l(s).size=t),onPagination:u},null,8,["total","page","limit"])):w("",!0)]),_:1}),e(A,{title:"退款",modelValue:l(y).visible,"onUpdate:modelValue":n[5]||(n[5]=t=>l(y).visible=t),width:"350px",onClose:g},{footer:o(()=>[I("div",be,[e(d,{type:"primary",onClick:R},{default:o(()=>[p("确定")]),_:1}),e(d,{onClick:g},{default:o(()=>[p("取消")]),_:1})])]),default:o(()=>[e(S,{model:l(i),ref_key:"fxsSetDayFormRef",ref:x,"label-width":"80px","label-position":"top",rules:l(B)},{default:o(()=>[e(h,{label:"设置分销商分享天数",prop:"fxsSetDay"},{default:o(()=>[e(Q,{modelValue:l(i).fxsSetDay,"onUpdate:modelValue":n[4]||(n[4]=t=>l(i).fxsSetDay=t),"controls-position":"right",min:0,precision:0,step:1,placeholder:"请输入",style:{width:"230px"}},null,8,["modelValue"])]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["modelValue"])])}}});const Be=me(ve,[["__scopeId","data-v-ea3b9d7b"]]);export{Be as default};
