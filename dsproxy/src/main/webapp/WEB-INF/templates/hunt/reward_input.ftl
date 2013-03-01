<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
${label_npc.name}：(赏金为银矿石)<br/>
击杀${label_target.name}一次的赏金:<br/>
	<input name="itemNum" maxlength="12" emptyok="false" type="text" /><br/>
击杀${label_target.name}的次数:<br/>
	<input name="maxPlayers" maxlength="12" emptyok="false" type="text" /><br/>
要求接任务的玩家级别(50-150)：<br/>
	<input name="playerLevel" maxlength="12" emptyok="false" type="text" /><br/>
<anchor>确定<br/>
<@go href="/p?h_I/${pid}/" method="post"/>
<postfield name="1" value="${label_npc.id}"/>
<postfield name="2" value="$itemNum"/>
<postfield name="3" value="$maxPlayers"/>
<postfield name="4" value="${label_target.id}"/>
<postfield name="5" value="$playerLevel"/>
</go>
</anchor>
<br/><@gofacility/>
</p>
</card>
</wml>