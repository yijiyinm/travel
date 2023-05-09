import{a as s,c as x,k as m,d as P,r as b,a7 as Q,o as D,I as e,w as o,f as l,aj as i,aa as j,l as _,V as c,aQ as A,aK as K,bi as O,aV as G,N as H}from "./index-d2e65462.js";import{e as J,d as W,_ as X,a as Y,E as Z,b as ee,c as te,v as oe}from "./search-10a84200.js";import{E as ne}from "./el-card-98c0779a.js";import{_ as le}from "./index-44ce9e20.js";import"./el-tooltip-852bd09b.js";import"./el-scrollbar-4f7614f6.js";import{E as $,a as ae}from "./el-form-item-7eacde13.js";import{a as se,d as re}from "./index-52dd6aae.js";import{_ as ie}from "./_plugin-vue_export-helper-c27b6911.js";const _e={viewBox:"0 0 1024 1024",width:"1.2em",height:"1.2em"},ce=m("path",{fill:"currentColor",d:"m466.752 512l-90.496-90.496a32 32 0 0 1 45.248-45.248L512 466.752l90.496-90.496a32 32 0 1 1 45.248 45.248L557.248 512l90.496 90.496a32 32 0 1 1-45.248 45.248L512 557.248l-90.496 90.496a32 32 0 0 1-45.248-45.248L466.752 512z"},null,-1),ue=m("path",{fill:"currentColor",d:"M512 896a384 384 0 1 0 0-768a384 384 0 0 0 0 768zm0 64a448 448 0 1 1 0-896a448 448 0 0 1 0 896z"},null,-1),de=[ce,ue];function pe(C, f){return s(),x("svg",_e,de)}const me={name:"ep-circle-close",render:pe},fe={viewBox:"0 0 1024 1024",width:"1.2em",height:"1.2em"},he=m("path",{fill:"currentColor",d:"M512 896a384 384 0 1 0 0-768a384 384 0 0 0 0 768zm0 64a448 448 0 1 1 0-896a448 448 0 0 1 0 896z"},null,-1),ge=m("path",{fill:"currentColor",d:"M745.344 361.344a32 32 0 0 1 45.312 45.312l-288 288a32 32 0 0 1-45.312 0l-160-160a32 32 0 1 1 45.312-45.312L480 626.752l265.344-265.408z"},null,-1),ve=[he,ge];function be(C, f){return s(),x("svg",fe,ve)}const ye={name:"ep-circle-check",render:be},we={class:"app-container"},ke={class:"search"},xe={name:"distribution"},Ce=P({...xe,setup(C){const f=b($),u=b(!1),d=b(0),a=Q({current:1,size:10}),E=b();function h(){u.value=!0,se(a).then(({data:r})=>{E.value=r,d.value=r==null?void 0:r.total}).finally(()=>{u.value=!1})}function V(){f.value.resetFields(),a.current=1,h()}function z({id:r,status:n}){const g={2:"通过",3:"不通过"};K.confirm(`确认${g[n]}该分销商吗?`,"提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(()=>{u.value=!0,re({id:r,status:n}).then(()=>{O.success(`${g[n]}成功`),V()}).finally(()=>u.value=!1)})}return D(()=>{h()}),(r, n)=>{const g=G,y=ae,w=J,B=W,T=X,v=H,L=Y,M=$,p=Z,k=ee,F=ye,N=me,U=te,I=le,R=ne,S=oe;return s(),x("div",we,[m("div",ke,[e(M,{ref_key:"queryFormRef",ref:f,model:l(a),inline:!0},{default:o(()=>[e(y,{prop:"phone",label:"手机号"},{default:o(()=>[e(g,{modelValue:l(a).phone,"onUpdate:modelValue":n[0]||(n[0]= t=>l(a).phone=t),placeholder:"请输入",clearable:""},null,8,["modelValue"])]),_:1}),e(y,{label:"状态",prop:"orderStatus"},{default:o(()=>[e(B,{modelValue:l(a).status,"onUpdate:modelValue":n[1]||(n[1]= t=>l(a).status=t),placeholder:"状态",clearable:""},{default:o(()=>[e(w,{value:1,label:"待审核"}),e(w,{value:2,label:"通过"}),e(w,{value:3,label:"不通过"})]),_:1},8,["modelValue"])]),_:1}),e(y,null,{default:o(()=>[e(v,{type:"primary",onClick:h},{default:o(()=>[e(T),i("搜索")]),_:1}),e(v,{onClick:V},{default:o(()=>[e(L),i("重置")]),_:1})]),_:1})]),_:1},8,["model"])]),e(R,{shadow:"never"},{default:o(()=>[j((s(),_(U,{ref:"dataTableRef",data:l(E),"highlight-current-row":"",selectable:"false",border:""},{default:o(()=>[e(p,{label:"id",prop:"id",width:"150"}),e(p,{label:"手机号",prop:"phone","min-width":"100"}),e(p,{label:"状态",align:"center",width:"100"},{default:o(t=>[t.row.status===1?(s(),_(k,{key:0,type:"warning"},{default:o(()=>[i("待审核")]),_:1})):c("",!0),t.row.status===2?(s(),_(k,{key:1,type:"success"},{default:o(()=>[i("通过")]),_:1})):c("",!0),t.row.status===3?(s(),_(k,{key:2,type:"info"},{default:o(()=>[i("不通过")]),_:1})):c("",!0)]),_:1}),e(p,{label:"申请备注",prop:"remark","min-width":"100"}),e(p,{fixed:"right",label:"操作",width:"220"},{default:o(t=>[t.row.status!==2?(s(),_(v,{key:0,type:"primary",size:"small",link:"",onClick: q=>z({id:t.row.id,status:2})},{default:o(()=>[e(F),i("通过 ")]),_:2},1032,["onClick"])):c("",!0),t.row.status!==3?(s(),_(v,{key:1,type:"primary",size:"small",link:"",onClick: q=>z({id:t.row.id,status:3})},{default:o(()=>[e(N),i("不通过 ")]),_:2},1032,["onClick"])):c("",!0)]),_:1})]),_:1},8,["data"])),[[S,l(u)]]),l(d)>0?(s(),_(I,{key:0,total:l(d),"onUpdate:total":n[2]||(n[2]= t=>A(d)?d.value=t:null),page:l(a).current,"onUpdate:page":n[3]||(n[3]= t=>l(a).current=t),limit:l(a).size,"onUpdate:limit":n[4]||(n[4]= t=>l(a).size=t),onPagination:h},null,8,["total","page","limit"])):c("",!0)]),_:1})])}}});const Ne=ie(Ce,[["__scopeId","data-v-b68adcc9"]]);export{Ne as default};
