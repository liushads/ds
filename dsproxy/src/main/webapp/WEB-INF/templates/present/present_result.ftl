<#include "/include/header.ftl">
<#include "/include/player.ftl">

<card title="${game_title}"><p>
<#if err_msg?exists && err_msg.code != 0>
${err_msg.text}<br/>
</#if>
<#if err_msg?exists && err_msg.code = 0>
你送给${receiver.name}的${item.name}已经送出，${receiver.name}会感觉格外的惊喜。<br/>

<@a href="/p?ps_M/${pid}/${receiver.id}"/>继续送礼给<#if receiver.sex=0>她<#else>他</#if></a><br/>
<@a href="/p?p_LF/${pid}/0"/>给好友送礼</a><br/>
</#if>
<@gogame/>
</p>
</card>
</wml>

 