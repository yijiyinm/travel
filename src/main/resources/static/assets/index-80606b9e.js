import{a as s,c as x,k as m,d as I,r as y,a7 as Q,o as A,K as e,w as o,f as l,aj as i,aa as D,l as _,A as c,aQ as K,aK as j,bi as O,aV as G,P as H}from"./index-bb12b684.js";import{E as J,g as W,f as X,_ as Y,a as Z,b as ee,c as te,d as oe,e as ne,v as le}from"./search-d96e9e3b.js";import"./el-tooltip-f69aa89d.js";import"./el-scrollbar-a96993cf.js";import{E as $,a as ae}from"./el-form-item-80e9f917.js";import{a as se,d as re}from"./index-f7c6f2ff.js";import{_ as ie}from"./_plugin-vue_export-helper-c27b6911.js";const _e={viewBox:"0 0 1024 1024",width:"1.2em",height:"1.2em"},ce=m("path",{fill:"currentColor",d:"m466.752 512l-90.496-90.496a32 32 0 0 1 45.248-45.248L512 466.752l90.496-90.496a32 32 0 1 1 45.248 45.248L557.248 512l90.496 90.496a32 32 0 1 1-45.248 45.248L512 557.248l-90.496 90.496a32 32 0 0 1-45.248-45.248L466.752 512z"},null,-1),ue=m("path",{fill:"currentColor",d:"M512 896a384 384 0 1 0 0-768a384 384 0 0 0 0 768zm0 64a448 448 0 1 1 0-896a448 448 0 0 1 0 896z"},null,-1),de=[ce,ue];function pe(C,f){return s(),x("svg",_e,de)}const me={name:"ep-circle-close",render:pe},fe={viewBox:"0 0 1024 1024",width:"1.2em",height:"1.2em"},he=m("path",{fill:"currentColor",d:"M512 896a384 384 0 1 0 0-768a384 384 0 0 0 0 768zm0 64a448 448 0 1 1 0-896a448 448 0 0 1 0 896z"},null,-1),ge=m("path",{fill:"currentColor",d:"M745.344 361.344a32 32 0 0 1 45.312 45.312l-288 288a32 32 0 0 1-45.312 0l-160-160a32 32 0 1 1 45.312-45.312L480 626.752l265.344-265.408z"},null,-1),ve=[he,ge];function ye(C,f){return s(),x("svg",fe,ve)}const be={name:"ep-circle-check",render:ye},we={class:"app-container"},ke={class:"search"},xe={name:"distribution"},Ce=I({...xe,setup(C){const f=y($),u=y(!1),d=y(0),a=Q({current:1,size:10}),E=y();function h(){u.value=!0,se(a).then(({data:r})=>{E.value=r==null?void 0:r.records,d.value=r==null?void 0:r.total}).finally(()=>{u.value=!1})}function z(){f.value.resetFields(),a.current=1,h()}function V({id:r,status:n}){const g={2:"通过",3:"不通过"};j.confirm(`确认${g[n]}该分销商吗?`,"提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(()=>{u.value=!0,re({id:r,status:n}).then(()=>{O.success(`${g[n]}成功`),z()}).finally(()=>u.value=!1)})}return A(()=>{h()}),(r,n)=>{const g=G,b=ae,w=W,B=X,T=Y,v=H,L=Z,M=$,p=ee,k=te,F=be,U=me,N=oe,P=ne,R=J,S=le;return s(),x("div",we,[m("div",ke,[e(M,{ref_key:"queryFormRef",ref:f,model:l(a),inline:!0},{default:o(()=>[e(b,{prop:"phone",label:"手机号"},{default:o(()=>[e(g,{modelValue:l(a).phone,"onUpdate:modelValue":n[0]||(n[0]=t=>l(a).phone=t),placeholder:"请输入",clearable:""},null,8,["modelValue"])]),_:1}),e(b,{label:"状态",prop:"orderStatus"},{default:o(()=>[e(B,{modelValue:l(a).status,"onUpdate:modelValue":n[1]||(n[1]=t=>l(a).status=t),placeholder:"状态",clearable:""},{default:o(()=>[e(w,{value:1,label:"待审核"}),e(w,{value:2,label:"通过"}),e(w,{value:3,label:"不通过"})]),_:1},8,["modelValue"])]),_:1}),e(b,null,{default:o(()=>[e(v,{type:"primary",onClick:h},{default:o(()=>[e(T),i("搜索")]),_:1}),e(v,{onClick:z},{default:o(()=>[e(L),i("重置")]),_:1})]),_:1})]),_:1},8,["model"])]),e(R,{shadow:"never"},{default:o(()=>[D((s(),_(N,{ref:"dataTableRef",data:l(E),"highlight-current-row":"",selectable:"false",border:""},{default:o(()=>[e(p,{label:"id",prop:"id",width:"150"}),e(p,{label:"手机号",prop:"phone","min-width":"100"}),e(p,{label:"状态",align:"center",width:"100"},{default:o(t=>[t.row.status===1?(s(),_(k,{key:0,type:"warning"},{default:o(()=>[i("待审核")]),_:1})):c("",!0),t.row.status===2?(s(),_(k,{key:1,type:"success"},{default:o(()=>[i("通过")]),_:1})):c("",!0),t.row.status===3?(s(),_(k,{key:2,type:"info"},{default:o(()=>[i("不通过")]),_:1})):c("",!0)]),_:1}),e(p,{label:"申请备注",prop:"remark","min-width":"100"}),e(p,{fixed:"right",label:"操作",width:"220"},{default:o(t=>[t.row.status!==2?(s(),_(v,{key:0,type:"primary",size:"small",link:"",onClick:q=>V({id:t.row.id,status:2})},{default:o(()=>[e(F),i("通过 ")]),_:2},1032,["onClick"])):c("",!0),t.row.status!==2?(s(),_(v,{key:1,type:"primary",size:"small",link:"",onClick:q=>V({id:t.row.id,status:3})},{default:o(()=>[e(U),i("不通过 ")]),_:2},1032,["onClick"])):c("",!0)]),_:1})]),_:1},8,["data"])),[[S,l(u)]]),l(d)>0?(s(),_(P,{key:0,total:l(d),"onUpdate:total":n[2]||(n[2]=t=>K(d)?d.value=t:null),page:l(a).current,"onUpdate:page":n[3]||(n[3]=t=>l(a).current=t),limit:l(a).size,"onUpdate:limit":n[4]||(n[4]=t=>l(a).size=t),onPagination:h},null,8,["total","page","limit"])):c("",!0)]),_:1})])}}});const Me=ie(Ce,[["__scopeId","data-v-290f7977"]]);export{Me as default};
