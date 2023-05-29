import{b as x,d as y,u as C,z as d,p as D,a as $,l as N,w as r,e as E,n as R,f as i,B as S,m as O,_ as V,s as B,g,ah as h,j as G,an as j,ai as L,a7 as z,t as J,aI as T,J as U,c as A,K as p,aQ as M,P as Q,aj as w,k as o,X,aD as q,aE as F}from "./index-9b9a7d02.js";import{E as H}from "./el-dialog-31bf0290.js";import{_ as W}from "./_plugin-vue_export-helper-c27b6911.js";import"./refs-d618e8be.js";const I=Symbol("rowContextKey"),Y=["start","center","end","space-around","space-between","space-evenly"],Z=["top","middle","bottom"],ee=x({tag:{type:String,default:"div"},gutter:{type:Number,default:0},justify:{type:String,values:Y,default:"start"},align:{type:String,values:Z,default:"top"}}),te=y({name:"ElRow"}),se=y({...te,props:ee,setup(n){const t=n,l=C("row"),a=d(()=>t.gutter);D(I,{gutter:a});const c=d(()=>{const e={};return t.gutter&&(e.marginRight=e.marginLeft=`-${t.gutter/2}px`),e}),f=d(()=>[l.b(),l.is(`justify-${t.justify}`,t.justify!=="start"),l.is(`align-${t.align}`,t.align!=="top")]);return(e, v)=>($(),N(O(e.tag),{class:R(i(f)),style:S(i(c))},{default:r(()=>[E(e.$slots,"default")]),_:3},8,["class","style"]))}});var oe=V(se,[["__file","/home/runner/work/element-plus/element-plus/packages/components/row/src/row.vue"]]);const ae=B(oe),le=x({tag:{type:String,default:"div"},span:{type:Number,default:24},offset:{type:Number,default:0},pull:{type:Number,default:0},push:{type:Number,default:0},xs:{type:g([Number,Object]),default:()=>h({})},sm:{type:g([Number,Object]),default:()=>h({})},md:{type:g([Number,Object]),default:()=>h({})},lg:{type:g([Number,Object]),default:()=>h({})},xl:{type:g([Number,Object]),default:()=>h({})}}),ne=y({name:"ElCol"}),ce=y({...ne,props:le,setup(n){const t=n,{gutter:l}=G(I,{gutter:d(()=>0)}),a=C("col"),c=d(()=>{const e={};return l.value&&(e.paddingLeft=e.paddingRight=`${l.value/2}px`),e}),f=d(()=>{const e=[];return["span","offset","pull","push"].forEach(s=>{const u=t[s];j(u)&&(s==="span"?e.push(a.b(`${t[s]}`)):u>0&&e.push(a.b(`${s}-${t[s]}`)))}),["xs","sm","md","lg","xl"].forEach(s=>{j(t[s])?e.push(a.b(`${s}-${t[s]}`)):L(t[s])&&Object.entries(t[s]).forEach(([u,b])=>{e.push(u!=="span"?a.b(`${s}-${u}-${b}`):a.b(`${s}-${b}`))})}),l.value&&e.push(a.is("guttered")),[a.b(),e]});return(e, v)=>($(),N(O(e.tag),{class:R(i(f)),style:S(i(c))},{default:r(()=>[E(e.$slots,"default")]),_:3},8,["class","style"]))}});var re=V(ce,[["__file","/home/runner/work/element-plus/element-plus/packages/components/col/src/col.vue"]]);const ue=B(re);const _= n=>(q("data-v-50c5d5d2"),n=n(),F(),n),ie={class:"errPage-container"},pe=_(()=>o("h1",{class:"text-jumbo text-ginormous"},"Oops!",-1)),de=_(()=>o("a",{href:"https://zh.airbnb.com/",target:"_blank"},"airbnb",-1)),_e=_(()=>o("h2",null,"你没有权限去该页面",-1)),fe=_(()=>o("h6",null,"如有不满请联系你领导",-1)),me={class:"list-unstyled"},be=_(()=>o("li",null,"或者你可以去:",-1)),ge={class:"link-type"},he=_(()=>o("li",{class:"link-type"},[o("a",{href:"https://www.taobao.com/"},"随便看看")],-1)),ye=["src"],we=["src"],ve={name:"Page401"},$e=y({...ve,setup(n){const t=z({errGif:new URL("/assets/401-a61ddb94.gif",self.location).href,ewizardClap:"https://wpimg.wallstcn.com/007ef517-bafd-4066-aae4-6883632d9646",dialogVisible:!1}),{errGif:l,ewizardClap:a,dialogVisible:c}=J(t),f=T();function e(){f.back()}return(v, m)=>{const s=Q,u=U("router-link"),b=ue,P=ae,K=H;return $(),A("div",ie,[p(s,{icon:"el-icon-arrow-left",class:"pan-back-btn",onClick:e},{default:r(()=>[w(" 返回 ")]),_:1}),p(P,null,{default:r(()=>[p(b,{span:12},{default:r(()=>[pe,w(" gif来源"),de,w(" 页面 "),_e,fe,o("ul",me,[be,o("li",ge,[p(u,{to:"/dashboard"},{default:r(()=>[w(" 回首页 ")]),_:1})]),he,o("li",null,[o("a",{href:"#",onClick:m[0]||(m[0]=X(k=>c.value=!0,["prevent"]))},"点我看图")])])]),_:1}),p(b,{span:12},{default:r(()=>[o("img",{src:i(l),width:"313",height:"428",alt:"Girl has dropped her ice cream."},null,8,ye)]),_:1})]),_:1}),p(K,{modelValue:i(c),"onUpdate:modelValue":m[1]||(m[1]= k=>M(c)?c.value=k:null),title:"随便看"},{default:r(()=>[o("img",{src:i(a),class:"pan-img"},null,8,we)]),_:1},8,["modelValue"])])}}});const Ne=W($e,[["__scopeId","data-v-50c5d5d2"]]);export{Ne as default};
