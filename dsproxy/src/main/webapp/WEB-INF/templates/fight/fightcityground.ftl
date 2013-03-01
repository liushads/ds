<#include "/include/header.ftl">
<card title="${game_title}">
<p>
${location}<br/>
<@a href="/p?mv_W/${pid}/${curr_facility.id}"/>刷新</a><br/>
<#if fight_enmy_num?exists && fight_self_num?exists>
敌${fight_enmy_num}人/我${fight_self_num}人<br/>
</#if>
你看到敌人:<br/>
<#if fight_enemy?exists>
<#list fight_enemy as ls>
${ls[0].name}(${ls[1].name}级)
<@a href="/p?f_M/${pid}/${ls[0].id}"/>攻击</a><br/>
</#list> 
</#if>
<@a href="/p?p_VS/${pid}/"/>状态</a>.
<@a href="/p?i_L/${pid}/1"/>物品</a>.
<@a href="/p?f_AB/${pid}/queren"/>撤退</a>
<br/>
</p>
</card>
</wml>
