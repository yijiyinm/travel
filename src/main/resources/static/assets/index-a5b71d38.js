import{d as b,aH as q,ay as B,r,o as F,a as K,c as R,K as s,w as i,k as c,f as o,au as z,X as D,aj as N,q as x,aV as U,P as $,aD as j,aE as M,aC as O}from "./index-9b9a7d02.js";import{E as v,a as P}from "./el-form-item-db6c4a7b.js";import{E as T}from "./el-tooltip-3d728abb.js";import{S as g}from "./index-d6daf1fa.js";import{_ as A}from "./_plugin-vue_export-helper-c27b6911.js";const H= d=>(j("data-v-e83cd487"),d=d(),M(),d),L={class:"login-container"},Q=H(()=>c("div",{class:"flex text-white items-center py-4"},[c("span",{class:"text-2xl flex-1 text-center"},x("后台管理系统"))],-1)),X={class:"p-2 text-white"},Z={class:"p-2 text-white"},G=b({__name:"index",setup(d){const k=q(),V=B(),_=r(!1),y=r(!1),u=r(!1),h=r(v),n=r({username:"",password:""}),E={username:[{required:!0,trigger:"blur",message:"请输入用户名"}],password:[{required:!0,trigger:"blur",validator:C}]};function C(t, e, a){e.length<6?a(new Error("密码长度不能小于6位")):a()}function S(t){const{key:e}=t;y.value=e&&e.length===1&&e>="A"&&e<="Z"}function w(){h.value.validate(t=>{t&&(_.value=!0,k.login(n.value).then(()=>{const e=V.query,a=e.redirect??"/",p=Object.keys(e).reduce((m, l)=>(l!=="redirect"&&(m[l]=e[l]),m),{});O.push({path:a,query:p})}).catch(()=>{}).finally(()=>{_.value=!1}))})}return F(()=>{}),(t, e)=>{const a=U,p=P,m=T,l=$,I=v;return K(),R("div",L,[s(I,{ref_key:"loginFormRef",ref:h,model:o(n),rules:E,class:"login-form"},{default:i(()=>[Q,s(p,{prop:"username"},{default:i(()=>[c("div",X,[s(g,{"icon-class":"user"})]),s(a,{class:"flex-1",ref:"username",size:"large",modelValue:o(n).username,"onUpdate:modelValue":e[0]||(e[0]= f=>o(n).username=f),placeholder:t.$t("login.username"),name:"username"},null,8,["modelValue","placeholder"])]),_:1}),s(m,{disabled:o(y)===!1,content:"Caps lock is On",placement:"right"},{default:i(()=>[s(p,{prop:"password"},{default:i(()=>[c("span",Z,[s(g,{"icon-class":"password"})]),s(a,{class:"flex-1",modelValue:o(n).password,"onUpdate:modelValue":e[1]||(e[1]= f=>o(n).password=f),placeholder:"密码",type:o(u)===!1?"password":"input",size:"large",name:"password",onKeyup:[S,z(w,["enter"])]},null,8,["modelValue","type","onKeyup"]),c("span",{class:"mr-2",onClick:e[2]||(e[2]= f=>u.value=!o(u))},[s(g,{"icon-class":o(u)===!1?"eye":"eye-open",class:"text-white cursor-pointer"},null,8,["icon-class"])])]),_:1})]),_:1},8,["disabled"]),s(l,{size:"default",loading:o(_),type:"primary",class:"w-full",onClick:D(w,["prevent"])},{default:i(()=>[N(x(t.$t("login.login")),1)]),_:1},8,["loading","onClick"])]),_:1},8,["model"])])}}});const oe=A(G,[["__scopeId","data-v-e83cd487"]]);export{oe as default};
