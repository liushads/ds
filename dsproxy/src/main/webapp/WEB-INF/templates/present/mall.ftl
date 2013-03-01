<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
礼品店<br/>
<#if msg?exists>${msg}<br/></#if>
<@showAllMoney player/>

<#if items?exists>
	<#list items as item>
		<img alt="" src="/images/presents/16/${item.id}.gif"/>
		<#if (item.id>9)>
		<@a href="/p?ps_GA/${pid}/${receiver.id}/${item.id}"/>送出</a>
		<#else>
		<@a href="/p?ps_BS/${pid}/${receiver.id}/${item.id}"/>送出</a>
		</#if>
		<#if item.gold=0>
		<@a href="/p?ps_VS/${pid}/${receiver.id}/${item.id}"/>${item.name}(${item.price/1000}银)</a>
		<#else>
		<@a href="/p?ps_VS/${pid}/${receiver.id}/${item.id}"/>${item.name}(${(item.gold/100)?int}金)</a>
		</#if><br/>
	</#list>
</#if>
<@goback />
<@gogame/>
</p>
</card>
</wml>

 