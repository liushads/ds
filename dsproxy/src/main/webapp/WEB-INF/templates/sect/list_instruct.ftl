<#-- 显示可以学习的技能列表 -->
<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if last_npc?exists>${last_npc.name}:</#if>
<#if err_msg?exists && err_msg.code != 0 >
<#if desc?exists>${desc}。<br/></#if>
<#elseif (err_msg?exists && err_msg.code = 0)>
你想要学习什么呢?<br/>
【<#list skillType?keys as key>
	<@showSkillType key,return/><#if key_has_next>|</#if>
</#list>】
<br/>
<#if sect_skills?exists || sect_advanced_skills?exists>
<#if sect_skills?exists>
	【可学】<br/>
	<#list sect_skills as skill>
	<@a href="/p?se_TS/${pid}/${sect_id}/${skill.id}/${return}/"/>学习</a> <@showSkillRequireUrl sect_id,skill,return/>
	</#list>
</#if>
<#-- 高级技能 -->
<#if sect_advanced_skills?exists>
	【高级】<br/>
	<#list sect_advanced_skills as skill>
	<@a href="/p?se_TS/${pid}/${sect_id}/${skill.id}/${return}/"/>学习</a> <@showSkillRequireUrl sect_id,skill,return/>
	</#list>
</#if>
</#if>
<#if !sect_skills?exists && !sect_advanced_skills?exists>
暂时没有可学的技能.<br/>
</#if>
</#if>
<br/>
<@gofacility/>
</p></card>
</wml>