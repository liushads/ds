<#-- 显示已经修炼的技能列表 -->
<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
【技能管理界面】<br/>
<#if !sect_skill_jx?exists || !sect_skill_zs?exists || !sect_skill_xf?exists>
【提示】你当前还没有学习<#if !sect_skill_jx?exists && !sect_skill_zs?exists && !sect_skill_xf?exists>任何技能
<#elseif !sect_skill_jx?exists || sect_skill_jx?size < 0 >绝学
<#elseif !sect_skill_zs?exists || sect_skill_zs?size < 0>招式
<#elseif !sect_skill_xf?exists || sect_skill_xf?size < 0>心法
</#if>,当你等级到达10级以后可以到<@outMainCityFacility player.sectId/>的【传功长老】处学习.
<br/>
</#if>
【绝学】<#if sect_skill_jx?exists><br/>
	<#list sect_skill_jx as skill> <@showUpgradePreUrl skill/>
	</#list>
<#else>
无<br/>
</#if>
【招式】<#if sect_skill_zs?exists><br/>
	<#list sect_skill_zs as skill> <@showUpgradePreUrl skill/>
	</#list>
<#else>
无<br/>
</#if>
【心法】<#if sect_skill_xf?exists><br/>
	<#list sect_skill_xf as skill> <@showUpgradePreUrl skill/>
	</#list>
<#else>无<br/></#if>
【生活技能】<br/>
【<@a href="/p?se_LF/${pid}/0/4734/10"/>采集</a>‖<@a href="/p?se_LF/${pid}/0/4738/12"/>锻造</a>‖<@a href="/p?se_LF/${pid}/0/4746/13"/>制药</a>‖<@a href="/p?se_LF/${pid}/0/4742/11"/>分解</a>‖】<br/>

<@a href="/p?p_VS/${pid}/"/>返回</a><br/>
<@gogame/>
</p></card>
</wml>