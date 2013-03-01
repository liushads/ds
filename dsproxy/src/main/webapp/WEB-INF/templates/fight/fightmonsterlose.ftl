<#include "/include/header.ftl">
<card title="${game_title}">
<p>
你被${be_killed}狠狠教训了一顿!<br/>
装备耐久减少10%<br/>
<#if fightEnemyInfo?exists>
${fightEnemyInfo}<br/>
</#if>
<#if waste_items?exists>
以下装备耐久为0已卸下，请尽快修理:<br/>
<#list waste_items as ls>${ls[0].name}<br/></#list>
</#if>
<#--<#if lost_money?exists>丢失铜贝:${lost_money}<br/></#if>-->
<#--<#if lost_items?exists>丢失装备:<#list lost_items as ls>${ls[0].name}<br/></#list></#if>-->
<#--<#if lost_ships?exists>丢失船只:<#list lost_ships as ls>${ls[0].name}<br/></#list></#if>-->
<#--<#if lost_supply?exists>丢失补给:${lost_supply}<br/></#if>-->
<#--<#if lost_morale?exists>士气减少:${lost_morale}<br/></#if>-->
被好心人救回了城里<br/>
<#if curr_facility?exists>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>继续</a><br/>
</#if>
</p>
</card>
</wml>
