<#include "/include/header.ftl">
<card>
<p>

<#if tong_player?exists>
	<#assign fixedAssets = tong_level.fixedAssets />
	<#-- 用户属于帮会 -->
	<#if (tong_player.level==1)>
		<#-- 用户是帮主 -->
		你当前是${tong.name}帮主<br/>
		帮规：${tong.description}（<@a href="/p?t_E/${pid}/"/>修改</a>）<br/>
		帮会等级：${tong.level}<br/>
		下一任帮主:<#if (tong.nextOwnerId>0)>${tong.nextOwnerName}<#else>无</#if><br/>
		帮会人数：${tong.amount}<br/>
		帮会城市：<#if tong_cities?size==0>无<#else><#list tong_cities as city>${city.name} </#list></#if><br/>
		帮会玩家福利：<#if (tong.copper>fixedAssets)>${tong.copper-tong_level.fixedAssets}<#else>0</#if><br/>
		帮会威望：${tong.fame}<br/>
		你的贡献度:${tong_player.mark}<br/>
		最新公告:<#if tong_notice?exists><@a href="/p?t_VN/${pid}/${tong_notice.id}"/>${tong_notice.title}</a><#else>无</#if><br/>
		<br/>
		<@a href="/p?t_PIL/${pid}/1"/>帮会捐赠</a><br/>
		<@a href="/p?t_DIL/${pid}/1/0"/>帮会仓库</a><br/>
		<@a href="/p?ca_L/${pid}/0/2/"/>帮会聊天</a><br/>
		<@a href="/p?t_NL/${pid}/"/>帮会公告</a><br/>
		<@a href="/p?t_Ex/${pid}/"/>帮贡换钱</a><br/>
		<@a href="/p?t_MI/${pid}/"/>帮会管理</a><br/>
		<@a href="/p?t_ML/${pid}/"/>帮友列表</a><br/>
		<@a href="/p?t_LS/${pid}/"/>帮会大事</a><br/>
		<#if tongName?exists && tongName=='qq'>
		<@a href="/p?t_McN/${pid}/"/>帮会改名</a><br/>
		</#if>
		<@a href="/p?t_Q/${pid}/"/>脱离帮会</a><br/>
	<#else>
		<#if tong_player.state==0>
		你正在申请加入${tong.name},请耐心等待帮主审批。<br/>
		<@a href="/p?t_C/${pid}/"/>撤销申请</a><br/>
		<#else>
		你当前属于${tong.name}<br/>
		帮主：<@a href="/p?p_VO/${pid}/${tong.ownerId}/"/>${tong.ownerName}</a><br/>
		帮规：${tong.description}<br/>
		帮会等级：${tong.level}<br/>
		下一任帮主:<#if (tong.nextOwnerId>0)>${tong.nextOwnerName}<#else>无</#if><br/>
		帮会人数：${tong.amount}<br/>
		帮会城市：<#if tong_cities?size==0>无<#else><#list tong_cities as city>${city.name} </#list></#if><br/>
		帮会玩家福利：<#if (tong.copper>fixedAssets)>${tong.copper-tong_level.fixedAssets}<#else>0</#if><br/>
		帮会威望：${tong.fame}<br/>
		你的帮内排名：${player_rank}<br/>
		你的贡献度:${tong_player.mark}<br/>
		最新公告:<#if tong_notice?exists><@a href="/p?t_VN/${pid}/${tong_notice.id}"/>${tong_notice.title}</a><#else>无</#if><br/>
		<br/>
		<@a href="/p?t_PIL/${pid}/1"/>帮会捐赠</a><br/>
		<@a href="/p?t_DIL/${pid}/1/0"/>帮会仓库</a><br/>
		<@a href="/p?ca_L/${pid}/0/2/"/>帮会聊天</a><br/>
		<@a href="/p?t_NL/${pid}/"/>帮会公告</a><br/>
		<@a href="/p?t_Ex/${pid}/"/>帮贡换钱</a><br/>
		<#if tong_player.level==2>
		<@a href="/p?t_MI/${pid}/"/>帮会管理</a><br/>
		</#if>
		<@a href="/p?t_ML/${pid}/"/>帮友列表</a><br/>
		<@a href="/p?t_LS/${pid}/"/>帮会大事</a><br/>
		<@a href="/p?t_Q/${pid}/"/>脱离帮会</a><br/>
		</#if>
	</#if>
	
<#else>
	你还没有加入帮会,你可以加入帮会，或者<@a href="/p?t_Cr/${pid}/"/>创建帮会</a><br/>
	<#if tongs?exists && (tongs?size>0)>
	帮会列表：<br/>
	<#list tongs as t>
		<@a href="/p?t_V/${pid}/${t.id}"/>${t.name}</a><br/>
	</#list>
	</#if>
</#if>
	<br/>
	<@gogame/>
	</p>
</card>
</wml>
