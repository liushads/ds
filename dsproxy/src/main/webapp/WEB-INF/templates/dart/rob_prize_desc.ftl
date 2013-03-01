<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#include "award.ftl">
<#if label_award?exists>
	劫镖奖励：<br/>
	<@showAwart label_award />
	<@a href="/p?d_GRD/${pid}/1/gt/${player_dart_prize_id}/"/>领取</a><br/>
</#if> 
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">