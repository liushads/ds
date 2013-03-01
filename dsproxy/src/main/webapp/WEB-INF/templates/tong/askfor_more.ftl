<#include "/include/header.ftl">
<card title="${game_title}"><p>
仓库现有${tong_depot.amount}个${tong_depot.item.name},请输入你索要的${tong_depot.item.name}数量:<br/>
个数：<input type="text" name="amount" value="1" format="*N" maxlength="3"></input><br/>
	<anchor>索要
        <@go href="/p?t_A/${pid}/" method="post"/>
                <postfield name="1" value="${tong_depot.id}" />
                <postfield name="2" value="$amount" />
                <postfield name="3" value="${tong_depot.itemId}" />
        </go>
	</anchor>	
<br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>