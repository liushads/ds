<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/item.ftl">
<card title="${game_title}"><p>
<@showItem item />
账户余额:<@showMoney player.copper />,${player.gold/100}金锭,${player.advGold/100}金票<br/>
请输入购买${item.name}的数量:<br/>
<input type="text" name="amount" value="1" format="*N" maxlength="3"></input><br/>
<anchor>购买
	<@go href="/p?i_BI/${pid}/" method="post"/>
		<postfield name="1" value="${item.id}" />
		<postfield name="2" value="$amount" />
	</go>
</anchor>
<#if item.gold &gt; 0>
<anchor>金票买
	<@go href="/p?i_BI/${pid}/" method="post"/>
		<postfield name="1" value="${item.id}" />
		<postfield name="2" value="$amount" />
		<postfield name="3" value="1" />
	</go>
</anchor>
<anchor>金锭买
	<@go href="/p?i_BI/${pid}/" method="post"/>
		<postfield name="1" value="${item.id}" />
		<postfield name="2" value="$amount" />
		<postfield name="3" value="2" />
	</go>
</anchor>
<br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>