<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
<#if label_desc?exists>${label_desc}<br/></#if>
你可以选择开启/关闭下列设置:<br/>
<#if label_fight_desc?exists>
<#if label_fight_desc==0>战斗描述已开启<@a href="/p?p_SS/${pid}/fd/1/"/>关闭</a></#if>
<#if label_fight_desc==1>战斗描述已关闭<@a href="/p?p_SS/${pid}/fd/0/"/>开启</a></#if>
<br/>
</#if>
<#if label_auto_hp?exists>
<#if label_auto_hp==0>自动补血已开启<@a href="/p?p_SS/${pid}/ah/1/"/>关闭</a></#if>
<#if label_auto_hp==1>自动补血已关闭<@a href="/p?p_SS/${pid}/ah/0/"/>开启</a></#if>
<br/>
</#if>
<#if label_auto_state?exists>
<#if label_auto_state==0>自动恢复状态已开启<@a href="/p?p_SS/${pid}/as/1/"/>关闭</a></#if>
<#if label_auto_state==1>自动恢复状态已关闭<@a href="/p?p_SS/${pid}/as/0/"/>开启</a></#if>
<br/>
</#if>
<#if label_auto_pick?exists>
<#if label_auto_pick==0>自动捡起道具已开启<@a href="/p?p_SS/${pid}/ap/1/"/>关闭</a></#if>
<#if label_auto_pick==1>自动捡起道具已关闭<@a href="/p?p_SS/${pid}/ap/0/"/>开启</a></#if>
<br/>
</#if>
<#if label_auto_mp?exists>
<#if label_auto_mp==0>自动恢复内力已开启<@a href="/p?p_SS/${pid}/am/1/"/>关闭</a></#if>
<#if label_auto_mp==1>自动恢复内力已关闭<@a href="/p?p_SS/${pid}/am/0/"/>开启</a></#if>
<br/>
</#if>
<#if label_friend_desc?exists>
<#if label_friend_desc==0>亲友提示已开启<@a href="/p?p_SS/${pid}/sf/1/"/>关闭</a></#if>
<#if label_friend_desc==1>亲友提示已关闭<@a href="/p?p_SS/${pid}/sf/0/"/>开启</a></#if>
<br/>
</#if>
<#if label_mission_desc?exists>
<#if label_mission_desc==0>任务跟踪已开启<@a href="/p?p_SS/${pid}/rw/1/"/>关闭</a></#if>
<#if label_mission_desc==1>任务跟踪已关闭<@a href="/p?p_SS/${pid}/rw/0/"/>开启</a></#if>
<br/>
</#if>
<@a href="/p?p_SSC/${pid}/"/>卸下快捷栏</a><br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>