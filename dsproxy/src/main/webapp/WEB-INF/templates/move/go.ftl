<#include "/include/header.ftl">
<#include "/include/chat.ftl">
<card title="${game_title}">
<#if player.autoSail && !(dialog?exists && dialog.actions?exists && dialog.actions?size &gt; 0)>
<onevent type="ontimer">
<@go href="/p?mv_G/${pid}/" method="post"/></go>
</onevent>
<timer value="20"/>
</#if>
<p>
<#if (flag==1) >
<#--航行-->
	${area}，到${city}还有${distance_left}里。<br/>
	
	<#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >
		<#if dialog.icon?exists && dialog.icon != "">
			<img alt="" src="${img_server}${dialog.icon}" /><br/>
		</#if>
		${dialog.dialog}<br/>
	</#if>
	<#if dialog?exists && dialog.actions?exists && dialog.actions?size &gt; 0>
		<#list dialog.actions as ls>
			<#assign param_temp = ""/>
			<#if ls.param?exists><#assign param_temp = ls.param+"/"/></#if>
			<@a href="/p?${ls.command}/${pid}/${param_temp}"/>${ls.name}</a>
		</#list>
	<#else>
		<#if player.autoSail>
		<@a href="/p?mv_GS/${pid}/0/"/>关闭自动行走</a><br/>
		 <#else>
		<@a href="/p?mv_GS/${pid}/1/"/>开启自动行走</a><br/>
	</#if>
	<@a href="/p?mv_G/${pid}/"/>继续</a><br/>
		<#if private_msg?exists>
			<@showPrivateMessage private_msg=private_msg/>
		</#if>
		<#if page_objs?exists>
			<@showPublicMessage page_objs=page_objs/>
		</#if>
	<#if (facility_players?exists) && (facility_players?size &gt; 0)>
	前方目的地的玩家有：<br/>
		<#list facility_players?keys as k>
		<@a href="/p?p_VO/${pid}/${facility_players[k].id}"/>${facility_players[k].name}</a><br/>
		</#list>
	</#if>
	</#if>
<#elseif (flag==2)>
<#--到达城市-->
	${area}<br/>
	前方即将路过${city}；<br/>
	<@a href="/p?mv_G/${pid}/"/>继续</a>
	<@a href="/p?mv_W/${pid}/0/"/>进城</a>
</#if>
<br/>
</p>
</card>
</wml>