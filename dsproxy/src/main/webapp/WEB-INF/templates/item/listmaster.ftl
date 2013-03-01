<#-- 显示可以进行镶嵌的装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if items?exists>
以下为可镶嵌的装备，有什么可以帮助你吗？<br/>
	<#list items as pi>		
		<@a href="/p?i_I/${pid}/${pi.id}"/>${pi.item.name}</a>
		<@a href="/p?i_LSl/${pid}/${pi.id}"/>镶嵌</a><br/>
	</#list>
	<#--xx职业推荐镶嵌宝石xx<br/>-->
<#else>
	您当前没有可以镶嵌的装备，可以先进行打孔！<br/>
	<#if label_da_item?exists && (label_da_item?size>0)>
		<#list label_da_item as pi>
			<@a href="/p?i_I/${pid}/${pi.id}"/>${pi.item.name}</a>
			<@a href="/p?i_PT/${pid}/${pi.id}"/>打孔</a> <br/>
		</#list>
	</#if>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 