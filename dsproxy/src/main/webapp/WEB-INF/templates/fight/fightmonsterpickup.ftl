<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}">
<#if (player.autoFightMonsterFlag==1)>
<#if curr_facility?exists>
<onevent type="ontimer">
<go href="<@encodeUrl "/p?f_M/${pid}/am/-1/", userKey, Md5Key/>"/>
</onevent>
<timer value="40"/>
</#if>
</#if>
<p>
<#if item?exists&&item.item.type==1&&item.item.id!=10406&&item.item.level<=player.level>
你获得了【${item.item.name}】<@a href="/p?i_UA/${pid}/${item.id}"/>一键换装</a><br/>
</#if>
你捡起了:<#if item?exists>${item.item.name}<br/>
<@showPlayerItem item />
</#if>
<#if mission?exists && mission != "">${mission}<br/>
<#if curr_facility?exists && player.level<=5>
(提示：点返回${curr_facility.name}，再点城内地图，可以快速抵达交任务的地点)<br/>
</#if>
</#if>
<#if win_prize?exists>
你看到:<br/>
<#list win_prize as ls>
<@a href="p?f_PU/${pid}/${ls[0].id}/${item.id}"/>${ls[0].name}</a><br/>
</#list>
</#if>
<@gofacility/>
<@a href="/p?p_VS/${pid}/"/>状态</a>.
<@a href="/p?i_L/${pid}/1/"/>物品</a>
<br/>
</p>
</card>
</wml>
