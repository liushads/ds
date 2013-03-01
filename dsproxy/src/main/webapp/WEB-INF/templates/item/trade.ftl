<#-- 显示可以进行属性转换的装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if player_item?exists&&player_item.isExchange==true>
	更改交易属性成功<br/>

	<#else>
	更改交易属性失败<br/>
		<@a href="/p?i_Tt/${pid}/${player_item.id}/"/>继续使用</a><br/>
</#if>
	
<@a href="/p?i_T/${pid}/${player_item.id}/"/>返回</a><br/>
<@gofacility/>
</p></card>
</wml> 