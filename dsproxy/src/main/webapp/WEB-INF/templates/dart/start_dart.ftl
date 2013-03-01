<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
每日12：30-14：30和20：00-22：00押镖可获得10%奖励加成<br/>
<#if label_desc?exists>${label_desc}<br/></#if>
<#if label_time?exists && (label_time == 0) && label_dart_name?exists>
	送${label_dart_name}车已完成运镖
	<@a href="/p?d_DP/${pid}/"/>领取奖励</a>
<#elseif label_time?exists && (label_time > 0) && label_dart_name?exists>
今日已进行运镖${label_today_time}次当前运镖剩余时间：${(label_time/60)?int}分${label_time%60}秒<br/>
<@speedUp />
<#else>
	<@a href="/p?d_SD/${pid}/bg/"/>开始运镖</a>
</#if><br/>
每日押镖次数：${label_today_time}/${label_today_all}<br/>
剩余劫镖次数：${label_today_rob}/${label_today_rob_all}<br/>
<#if label_dartvo?exists>
	<#list label_dartvo as vo>
	${vo.playerName} ${vo.playerLevel}级 ${vo.dartName}
	<#if vo.isRob == 1>
		<@a href="/p?d_RD/${pid}/${vo.playerId}/"/>劫镖</a>
	</#if>
	<br/>
	</#list>
</#if>
<@a href="/p?d_SD/${pid}/in/"/>刷新</a> <@a href="/p?d_SA/${pid}/"/>查看所有镖车</a><br/>
★最新动态★<br/>
<#if label_msg_list?exists>
	<#list label_msg_list as msg>
		${msg}<br/>
	</#list>
</#if>
<@a href="/p?d_GRD/${pid}/1/"/>劫镖排行</a><br/>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">