<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<#assign d={"w":"武器","g":"防具","k":"首饰","a":"暗器"}>
<#macro showType type target>
	<#if type=target>
		${d[type]}
	<#else>
		<@a href="/p?i_LFI/${pid}/${type}/0"/>${d[type]}</a>
	</#if>
</#macro>
<card title="${game_title}"><p>
精炼大师：<br/>
你好! 有什么可以为你帮忙的吗?
<br/>
<#if (page_objs?size > 0)>
【<#list d?keys as key>
	<@showType key,itemType/><#if key_has_next>|</#if>
</#list>】<br/>
		<#list page_objs as obj>
		<@a href="/p?i_LCM/${pid}/${obj.itemId}/298/"/>锻造</a> <@showItemNoBuy obj.item/>
			<br/>
		</#list>
		<#if (page>0)><@a href="p?i_LFI/${pid}/${itemType}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="p?i_LFI/${pid}/${itemType}/${page+1}"/>下页.</a></#if>
	</#if>
<br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>