<#include "/include/header.ftl">
<card title="${game_title}">
<#if (player.autoAttackFlag==1) && (atk_to?exists)>
<onevent type="ontimer">
<#if curr_jx?exists>
<go href="<@encodeUrl "/p?f_M/${pid}/${atk_to[0][0].id}/jx/${curr_jx.id}/", userKey, Md5Key/>"/>
<#else>
<go href="<@encodeUrl "/p?f_M/${pid}/${atk_to[0][0].id}", userKey, Md5Key/>"/>
</#if>
</onevent>
<#if time?exists>
<timer value="100"/>
<#else>
<timer value="20"/>
</#if>
</#if>
<p>
<#if (player.hp<player.dyn.maxHp/5)><img alt="" src="${img_server}/tlbz.gif"/><br/></#if>

<#if (atk_from?exists) && !atk_to?exists>
在${curr_facility.name}中，突然被袭击!<br/>
</#if>


<#if (type?exists)>
<#if (type="1") && (item?exists)>你已经将快捷栏${return}设为${item.item.name}<br/></#if>
<#if (type="2") && (item?exists)>使用了${item.item.name}<#if (item_add_hp?exists)>，体力+${item_add_hp}</#if><br/></#if>
</#if>

<#if (monsterAutostoredHp?exists) && (monsterAutostoredHp>0)>怪物自动恢复体力 +${monsterAutostoredHp}<br/></#if>
<#if (auto_hp?exists) && (auto_hp>0)>你自动恢复体力 +${auto_hp}<br/></#if>
<#if (autoAddEndure?exists) && (autoAddEndure>0)>你的装备恢复耐久 +${autoAddEndure}<br/></#if>
<#--<#if (auto_state?exists) && (auto_state>0)>使用急救箱自动恢复了状态<br/></#if>-->

<#if atk_to?exists>
	<#if atk_to_desc?exists>
		<#list atk_to_desc as ls>
		${ls[0].name}<br/><#if ls[0].id != "">${ls[0].id}<br/></#if>
		</#list>
	</#if>
	
	<@a href="/p?f_M/${pid}/${atk_to[0][0].id}/"/>攻击</a>.
	<#--<#if curr_jx?exists><@a href="/p?f_M/${pid}/${atk_to[0][0].id}/jx/${curr_jx.id}/"/>${curr_jx.name}</a>.
	<#else><@a href="/p?f_SC/${pid}/jx/0/"/>绝学</a>.</#if>-->
	<#if curr_aq?exists><@a href="/p?f_M/${pid}/${atk_to[0][0].id}/aq/${curr_aq.id}/"/>${curr_aq.name}</a>.
	<#else><@a href="/p?f_SC/${pid}/aq/0/"/>暗器</a></#if>
	<br/>
	
	<#list atk_to as ls>${ls[0].name}体力  (${ls[1].name}/${ls[1].id}) -${ls[2].name}<br/></#list>
</#if>
你体力    (${player.hp}/${player.dyn.maxHp}) -<#if dec_hp_sum?exists>${dec_hp_sum}<#else>0</#if><br/>
<#--你内力    (${player.mp}/${player.dyn.maxMp}) -<#if dec_mp_sum?exists>${dec_mp_sum}<#else>0</#if><br/>-->
<#--<#if pet_stat?exists>宠状态:${pet_stat}<br/></#if>-->
<#if atk_from?exists>
<#list atk_from as ls>
${ls[0].name}(${ls[1].name}/${ls[1].id}) <#if ls[2].id="">普<#else>${ls[2].id}</#if>攻(${ls[2].name})
<@a href="/p?f_M/${pid}/${ls[0].id}/"/>攻击</a>
<br/>
</#list>
</#if>

<#if shortcut?exists>
	<#list shortcut as ls>
	<@a href="/p?f_SC/${pid}/yp/${ls[0].id}/"/>${ls[0].name}</a>
	<#if ((ls_index+1)%3=0)><br/><#else>.</#if>
	</#list>
</#if>
<@a href="/p?p_VS/${pid}/"/>状态</a>.
<@a href="/p?i_L/${pid}/1/"/>物品</a><br/>
<#if atk_to?exists><@a href="/p?f_MV/${pid}/${atk_to[0][0].id}/"/>查看</a>.</#if>
<#if monster_abort?exists><#assign abort = monster_abort/><#else><#assign abort = 200/></#if>
<@a href="/p?f_AB/${pid}/queren/"/>撤退(${abort}铜)</a><br/>

<#if player?exists>
<#if player.autoFightMonsterFlag != 1>
<#if atk_to?exists>
	<@a href="/p?f_M/${pid}/${atk_to[0][0].id}/a/"/>
	<#if player.autoAttackFlag==0>开启<#else>关闭</#if>自动攻击</a><br/>
</#if>
</#if>
<#if atk_to?exists>
<#if player.level?exists && player.level &gt; 15>
<#--
<#if player.autoFightMonsterFlag == 0>
<@a href="/p?f_M/${pid}/am/${atk_to[0][0].id}"/>开启自动遇怪</a><br/>
<#else>
<@a href="/p?f_M/${pid}/as/${atk_to[0][0].id}/"/>停止自动遇怪</a><br/>
</#if>
-->
</#if>
</#if>
</#if>
<#if player_stat?exists>
你状态:${player_stat}<br/>
</#if>
<br/>
</p>
</card>
</wml>
