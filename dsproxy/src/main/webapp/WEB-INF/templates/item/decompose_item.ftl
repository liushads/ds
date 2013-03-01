<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#if label_descompose?exists && (label_descompose.itemVos?size>0)>
恭喜你得到<br/>
	<#list label_descompose.itemVos as item>
	<@a href="/p?i_II/${pid}/${item.id}/"/>${item.name}</a> * ${item.num}<br/>
	</#list>
<#else>
	<#if desc?exists>
	${desc}<br/>
	</#if>
</#if>
<@gogame/>
<#include "/include/foot_card.ftl">