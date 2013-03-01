<#-- 查看属性-->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if item_property?exists>	
特殊名:${item_property.name}<br/>
描     述:<#if item_property.description?exists>${item_property.description}<#else>无</#if><br/>
</#if>
<@goback />
<@gogame/>
</p></card>
</wml> 