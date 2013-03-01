<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#include "award.ftl">
每日12：30-14：30和20：00-22：00押镖可获得10%奖励加成<br/>
<#if label_dart?exists>
	<#list label_dart as dart>
		${dart.name}运送时间：${dart.allTime}分钟<br/>
		运送奖励：
		<#if dart.dartAwards?exists>
		 <@showAwart dart.dartAwards />
		</#if>
		<#--
		<#if dart.dartAwards?exists>
			<#if (dart.dartAwards.copper > 0)>
				${dart.dartAwards.copper}金币
			</#if>
			<#if (dart.dartAwards.gold > 0)>
				${dart.dartAwards.gold}钻石
			</#if>
			<#if dart.dartAwards.dartItems?exists>
				<#list dart.dartAwards.dartItems as dartItme>
					<#if (dartItme_index > 0)>、</#if>
					${dartItme.name}X${dartItme.amount}
				</#list>
			</#if>
			<br/>
		</#if>
		-->
	</#list>
</#if>

<#if label_player_dart?exists>
当前运送镖车：${label_player_dart.name}<@a href="/p?d_SD/${pid}/bg/g/"/>随机刷新每次1钻石</a><br/>
<@a href="/p?d_SD/${pid}/st/"/>确定开始运镖</a><br/>
</#if>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">