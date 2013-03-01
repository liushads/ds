<#-- 玩具输入要卖掉道具个数页面 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if player_item?exists>
<@showPlayerItem player_item />
请输入卖掉出数量:<br/>
<input type="text" name="amount" value="${player_item.amount}" format="*N" maxlength="3"></input><br/>
<anchor>卖出
	<@go href="/p?i_SeI/${pid}/" method="post"/>
		<postfield name="1" value="${player_item.id}" />
		<postfield name="2" value="$amount" />
		<postfield name="3" value="c" />
	</go>
</anchor>
<#else>
无
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>