<#-- 设置绝学快捷键 -->
<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if desc?exists>${desc}</#if>
<br/>
<@a href="/p?se_VS/${pid}/"/>返回</a><br/>
<@gofacility/>
</p></card>
</wml>