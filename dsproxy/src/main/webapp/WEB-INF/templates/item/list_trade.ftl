<#-- 显示可以进行属性转换的装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if items?exists>
	使用易洗符有几率改变装备的交易属性?<br/>
	<#list items as playerItem>
		<@a href="/p?i_Tt/${pid}/${playerItem.id}/"/>修改</a> <@a href="/p?i_I/${pid}/${playerItem.id}"/><@outStar playerItem/>${playerItem.item.name}<#if playerItem.improveLevel!=0>+${playerItem.improveLevel}</#if>(${playerItem.item.level}级)</a><br/>
	</#list>
<#else>
你身上没有不可以交易的装备.<br/>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 