<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
摆摊物品：<@showName player_item /><br/>
温馨提示:交易成功后系统会抽取10%的手续费。<br/>
<#if player_item.item.type == 1>
请输入交易的价格,1金票=100分(最低10分):<br/>
<input type="text" name="price" value="100" format="*N" maxlength="5"></input>单位(分)<br/>
<anchor>确定
	<@go href="/p?ex_P/${pid}/" method="post"/>
        <postfield name="1" value="${player_item.id}" />
        <postfield name="2" value="$price" />
    </go>
</anchor><br/>	
<#else>
请输入交易的数量:<br/>
<input type="text" name="amount" value="1" format="*N" maxlength="3"></input><br/>
请输入交易的价格,1金票=100分(最低10分):<br/>
<input type="text" name="price" value="100" format="*N" maxlength="5"></input>单位(分)<br/>
<anchor>确定
        <@go href="/p?ex_P/${pid}/" method="post"/>
                <postfield name="1" value="${player_item.id}" />
                <postfield name="2" value="$price" />
                <postfield name="3" value="$amount" />
        </go>
</anchor><br/>	
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>