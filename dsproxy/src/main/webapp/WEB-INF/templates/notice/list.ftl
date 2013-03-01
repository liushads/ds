<#include "/include/header.ftl">
<card title="最新公告"><p> 
<#if notices?exists>
最新公告：<br/>
骗子技巧层出不穷，请给位不要将QQ密码以及书签给任何人。<br/>
<#list notices as notice>
${notice_index+1}、<@a href="/p?n_V/${pid}/${notice.id}"/>${notice.title}</a><br/>
</#list>
</#if>
<br/>
<@goback/>
<@gogame/>
</p>  
</card>  
</wml> 