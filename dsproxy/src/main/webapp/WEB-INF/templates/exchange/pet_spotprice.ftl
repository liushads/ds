<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
摆摊物品：<@a href="/p?pe_ReP/${pid}/${player_pet.id}"/>${player_pet.customName}</a><br/>
温馨提示:交易成功后系统会抽取10%的手续费。<br/>
请输入交易的价格,1金票=100分(最低10分):<br/>
<input type="text" name="price" value="100" format="*N" maxlength="5"></input>单位(分)<br/>
<anchor>确定
	<@go href="/p?ex_PL/${pid}/" method="post"/>
        <postfield name="1" value="${player_pet.id}" />
        <postfield name="2" value="$price" />
    </go>
</anchor><br/>	
<@goback/>
<@gogame/>
</p></card>
</wml>