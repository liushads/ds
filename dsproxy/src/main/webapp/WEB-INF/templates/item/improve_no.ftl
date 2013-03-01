<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<#assign showContinue = 1/>
<card title="${game_title}"><p>
强化失败<br/>
<#if err_msg?exists>
${err_msg.text}
	<#if (err_msg.code < -3)>
		<@a href="/p?ma_I/${pid}/1/"/>购买</a><br/>
	</#if>
	<br/>
</#if>
<@a href="/p?i_IMT/${pid}/${label_player_id}"/>继续强化</a><br/>
<@goback/>
<@gofacility/>
</p></card>
</wml>