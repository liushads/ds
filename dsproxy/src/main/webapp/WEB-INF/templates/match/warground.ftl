<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location} <@a href="/p?mv_W/${pid}/${curr_facility.id}"/>刷新</a><br/>
你的战绩:<#if obj.winCount=0>暂无名次<#else>第${obj.index}名</#if>(${obj.winCount}胜)<br/>
你看到敌人(${left_enemy}/${total_enemy}):<br/>
<#if fight_enemy?exists>
<#list fight_enemy as ls>
${ls[0].name}(${ls[1].name}级)
<@a href="/p?f_M/${pid}/${ls[0].id}"/>攻击</a><br/>
</#list> 
</#if>
<@a href="/p?p_VS/${pid}/"/>状态</a>.
<@a href="/p?i_L/${pid}/1"/>物品</a>.
<@a href="/p?f_AB/${pid}/0"/>撤退</a>
<br/>
</p>
</card>
</wml>
