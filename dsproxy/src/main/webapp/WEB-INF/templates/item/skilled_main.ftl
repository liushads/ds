<#include "/include/header.ftl">
<card title="${game_title}"><p>
【生活技能】<br/>
【<@a href="/p?i_LSK/${pid}/0/4734/10"/>采集</a>‖<@a href="/p?i_LSK/${pid}/0/4738/12"/>锻造</a>‖<@a href="/p?i_LSK/${pid}/0/4746/13"/>制药</a>‖<@a href="/p?i_LSK/${pid}/0/4742/11"/>分解</a>‖】<br/>
<#if sect_skills?exists>
    <#list sect_skills as skill>
	<#if sect_next_skill?exists>
	<#if skill.type==10>${player.degreeCaiji}/${sect_next_skill.point}</#if>
	<#if skill.type==12>${player.degreeDuanzao}/${sect_next_skill.point}</#if>
	<#if skill.type==13>${player.degreeZhiyao}/${sect_next_skill.point}</#if>
	<#if skill.type==11>${player.degreeFenjie}/${sect_next_skill.point}</#if>  熟练度<#else>暂未开放更高等级的技能 </#if> <@a href="/p?i_SK/${pid}/${skill.sectSkill.type}"/>对${skill.sectSkill.name}使用熟练丹</a>
	</#list>
<#else>
        请在游戏界面-状态-技能-生活技能处学习技能
</#if><br/>
<@gogame/>
</p>
</card>
</wml>