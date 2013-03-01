<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if player_item?exists>
	丢弃后将无法恢复，请确认是否要丢弃${player_item.item.name}?<br/>
	<#if player_item.item.type == 1><#-- 装备类 -->
		<@a href="/p?i_Dr/${pid}/${player_item.id}/1"/>确认丢弃</a><br/>
	<#else><#-- 恢复类 -->
		可丢弃数量:${player_item.amount}<br/>
		<input type="text" name="amount" value="${player_item.amount}" format="*N" maxlength="3"></input><br/>
		<anchor>确认丢弃
			<@go href="/p?i_Dr/${pid}/${player_item.id}/" method="post"/>
				<postfield name="2" value="$amount" />
				<postfield name="3" value="${player_item.item.id}" />
			</go>
		</anchor><br/>
	</#if>
<#else>
	你没有该物品
</#if>
<@goback />
</p></card>
</wml>


 