<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}">
<p>
敌方属性<br/>
<#if monster?exists>
<#if monster_name?exists>
名称:${monster_name}<br/>
<#else>
名称:${monster.name}<br/>
</#if>
等级:${monster.level}<br/>
体力:${monster.maxHp}<br/>
攻击:${monster.attackMin}-${monster.attackMax}<br/>
防御:${monster.defence}<br/>
敏捷:${monster.agility}<br/>
<#--<@showSpecProp monster_player.dyn/>-->
</#if>

<#if enemy?exists>
名称:${enemy.name}<br/>
等级:${enemy.level}<br/>
体力:${enemy.dyn.maxHp}<br/>
攻击:${enemy.dyn.attackMin}-${enemy.dyn.attackMax}<br/>
防御:${enemy.dyn.defence}<br/>
敏捷:${enemy.dyn.agility}<br/>
<#--<@showSpecProp enemy.dyn/>-->
</#if>

<@goback />
<#if curr_facility?exists>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>返回${curr_facility.name}</a><br/>
</#if>
</p>
</card>
</wml>
