<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#if label_decompose?exists && (label_decompose?size>0)>
	<#list label_decompose as playerItem>
	<@a href="/p?i_I/${pid}/${playerItem.id}/"/>${playerItem.item.name}</a> 
	<@a href="/p?i_DI/${pid}/${playerItem.id}/ok/"/>分解</a>
	<br/>
	</#list>
<#else>
无<br/>
</#if>
<@gogame/>
<#include "/include/foot_card.ftl">