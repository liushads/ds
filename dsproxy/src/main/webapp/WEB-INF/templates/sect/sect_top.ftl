<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}">
<#assign sect_top={"SECT_SKILL":"狂人榜","SECT_COACH":"名师榜","SECT_RICHER":"富豪榜"}/>
<p>
<#if sectTopList?exists>
【${sect_name[sectId]}${sect_top[skey]}】<br/>
<#if sectTopList?size &gt; 0>
<#list sectTopList as sectTop>
	&nbsp;&nbsp;${sectTop.sname}&nbsp;&nbsp;<@a href="/p?p_VO/${pid}/${sectTop.player.id}/"/>${sectTop.player.name}</a><br/>
</#list>
<#else>
暂无排名<br/>
</#if>
<#else>
【${sect_name[sectId]}概况】<br/>
总人数：<#if sectTotalPlayer?exists>${sectTotalPlayer.svalue}<#else>0</#if><br/>
<@a href="/p?se_T/${pid}/${sectId}/SECT_SKILL/"/>狂人榜</a><br/>
<@a href="/p?se_T/${pid}/${sectId}/SECT_COACH/"/>名师榜</a><br/>
<@a href="/p?se_T/${pid}/${sectId}/SECT_RICHER/"/>富豪榜</a><br/>
<@a href="/p?mc_SH/${pid}/${sectId}/0/"/>荣誉堂</a><br/>
</#if>

<@goback/>
<@gogame/>
</p>
</card>
</wml>