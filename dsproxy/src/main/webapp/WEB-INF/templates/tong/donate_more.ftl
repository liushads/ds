<#include "/include/header.ftl">
<card title="${game_title}"><p>
你当前拥有${player_item.amount}个${player_item.item.name},请输入要捐献的物品数量:<br/>
<input type="text" name="amount" value="1" format="*N" maxlength="3"></input><br/>
	<anchor>传送
        <@go href="/p?t_D/${pid}/" method="post"/>
                <postfield name="1" value="${player_item.id}" />
                <postfield name="2" value="${player_item.item.id}" />
                <postfield name="3" value="${player_item.item.type}" />
                <postfield name="4" value="$amount" />
        </go>
	</anchor>
<br/>
<br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>
