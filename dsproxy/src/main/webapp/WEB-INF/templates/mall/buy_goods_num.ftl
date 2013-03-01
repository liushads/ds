<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<#assign gold = goods.gold/100>
<card title="${game_title}"><p>
名称:${goods.name}<br/>
说明:${goods.description}<br/>
负重:${goods.room}<br/>
价格:${gold}水晶<br/>
【账户信息】<br/>
<@showAllMoney player,-1/>
负重:${player.room}/${player.dyn.maxRoom}<br/>

<input type="text" name="amount" value="1" format="*N"  maxlength="3"></input><br/>
<#if goods.buyType == 0>
<anchor>水晶购买
	<@go href="/p?ma_GBG/${pid}/" method="post"/>
			<postfield name="1" value="${goods.id}" />
			<postfield name="2" value="$amount" />
			<postfield name="3" value="0" />
	</go>
</anchor>	
</#if>	
<anchor>钻石购买
	<@go href="/p?ma_GBG/${pid}/" method="post"/>
			<postfield name="1" value="${goods.id}" />
			<postfield name="2" value="$amount" />
			<postfield name="3" value="1" />
	</go>
</anchor>	
<br/>
<@goback/>
<@gogame/>
</p></card>
</wml>