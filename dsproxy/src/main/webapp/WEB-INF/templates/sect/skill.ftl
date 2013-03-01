<#-- 显示技能详细信息 -->
<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
<#if sect_skill?exists>
<@showSectSkill sect_skill/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>