<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<#include "/include/pet.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
<#if pid!=other.id><@a href="/p?m_I/${pid}/${other.id}"/>私聊</a>.<@a href="/p?p_AF/${pid}/${other.id}/"/>加好友</a>.<@a href="/p?p_AB/${pid}/${other.id}/1"/>黑名单</a>.<br/>
<#if other.coach?exists><@a href="/p?co_BS/${pid}/${other.id}/"/>拜师</a>.</#if><@a href="/p?i_SG/${pid}/${other.id}"/>赠送</a>.<@a href="/p?te_J/${pid}/${other.teamId}/"/>加队伍.</a><#if player?exists && (player.cityFacilityId?exists)&& (player.location != '旅途中')><@a href="/p?f_M/${pid}/${other.id}/"/>攻击</a>.</#if><br/>
<@a href="/p?p_NL/${pid}/${other.id}/"/>缘份.</a>
<@a href="/p?ps_M/${pid}/${other.id}/"/>送礼.</a> <@a href="/p?ex_SP/${pid}/${other.id}/0/"/>摆摊.</a></#if>
<#if player.gm?exists><#if player.gm.isJail><#if other.chatStatus?exists && (other.chatStatus &gt; -1)><@a href="p?p_J/${pid}/free/${other.id}/"/>解禁</a>.<#else><@a href="p?p_J/${pid}/view/${other.id}/"/>禁言</a>.</#if></#if></#if><br/>
<img alt="" src="${img_server}/${other.sectId}_${other.sex}.gif"/><br/>
昵称:${other.name}<br/>
ID&nbsp;&nbsp;:${other.id}<br/>
身份:
<#--<#if other.bookVip?exists && other.bookVip == 1>
<img alt="" src="${img_server}/bookstore_vip.gif"/>
</#if>-->
<#if other.playerMember?exists><img alt="" src="${img_server}/member_vip_${other.playerMember.memberLevel}.png"/>会员用户<#else>普通用户</#if><br/>
等级:${other.level}级${sex_name[other.sex?string]}<br/>
婚姻:${other.mate}<br/>
门派:<@a href="/p?se_T/${pid}/${other.sectId}"/>${sect_name[other.sectId?string]}</a><br/> 
擅长:【<@outAdept other.sectId />】<br/>
头衔:<#if other.title?exists>${other.title}<#else>无</#if><br/>
宠物:<@showPetUrl other/><br/>
<@viewOtherUseArm objs, other/><br/>
<#if tong?exists>
帮会:<@a href="/p?t_V/${pid}/${tong.id}"/>${tong.name}</a><br/>
</#if>
<@goback/>
</p>
</card>
</wml>