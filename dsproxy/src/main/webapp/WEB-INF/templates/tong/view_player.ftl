<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<#include "/include/pet.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
<@a href="/p?m_I/${pid}/${other.id}"/>私聊</a>.<@a href="/p?p_AF/${pid}/${other.id}/"/>加好友</a>.<@a href="/p?p_AB/${pid}/${other.id}/"/>黑名单</a><br/>
<#if player?exists && (player.cityFacilityId?exists)&& (player.location != '旅途中')><@a href="/p?f_M/${pid}/${other.id}/"/>攻击</a>.</#if><@a href="/p?ps_M/${pid}/${other.id}/"/>送礼.</a><@a href="/p?ex_SP/${pid}/${other.id}/0/"/>摆摊</a><br/>
<#if other.coach?exists><@a href="/p?co_BS/${pid}/${other.id}/"/>拜师</a>.</#if><@a href="/p?i_SG/${pid}/${other.id}"/>赠送</a>.<@a href="/p?te_J/${pid}/${other.teamId}/"/>加队伍.</a><br/>
昵称:${other.name}<br/>
${other.level}级${sex_name[other.sex?string]}<br/>
婚姻:${other.mate}<br/>
门派:${sect_name[other.sectId?string]}<br/> 
擅长:【<@outAdept other.sectId />】<br/>
头衔:<#if other.title?exists>${other.title}<#else>无</#if><br/>
宠物:<@showPetUrl other/><br/>
<@viewOtherUseArm objs, other/><br/>

<#if status=="ap">
<@a href="/p?t_MAuA/${pid}/allow/${other.id}"/>批准</a>
<@a href="/p?t_MAuA/${pid}/refuse/${other.id}"/>拒绝</a><br/>
<#elseif status=="mp">
	当前帮会等级:${tong_title}<br/>
	<@a href="/p?t_MPA/${pid}/set/${target_tong_player.playerId}/2"/>设置为副帮主</a><br/>
	<@a href="/p?t_MPA/${pid}/set/${target_tong_player.playerId}/3"/>设置为香主</a><br/>
	<@a href="/p?t_MPA/${pid}/set/${target_tong_player.playerId}/0"/>设置为帮众</a><br/>
	<@a href="/p?t_MPA/${pid}/nominate/${target_tong_player.playerId}/0"/>设置为下一任帮主</a><br/>
	<@a href="/p?t_MPA/${pid}/kick/${target_tong_player.playerId}/0"/>踢出帮会</a><br/>
</#if>
<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
<@goback/>
</p>
</card>
</wml>