<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
【生活技能】<br/>
【<@a href="/p?se_LF/${pid}/0/4734/10"/>采集</a>‖<@a href="/p?se_LF/${pid}/0/4738/12"/>锻造</a>‖<@a href="/p?se_LF/${pid}/0/4746/13"/>制药</a>‖<@a href="/p?se_LF/${pid}/0/4742/11"/>分解</a>‖】
<br/>
	<#list sect_skills as skill>
	<#if sect_next_skill?exists>
	<#if skill.type==10>${player.degreeCaiji}/${sect_next_skill.point}</#if>
	<#if skill.type==12>${player.degreeDuanzao}/${sect_next_skill.point}</#if>
	<#if skill.type==13>${player.degreeZhiyao}/${sect_next_skill.point}</#if>
	<#if skill.type==11>${player.degreeFenjie}/${sect_next_skill.point}</#if>  熟练度<#else>暂未开放更高等级的技能 </#if> <@showUpgradePreUrl skill/>  
	</#list>
<@gogame/>
</p></card>
</wml>