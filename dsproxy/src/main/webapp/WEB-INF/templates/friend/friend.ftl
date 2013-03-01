<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if desc?exists>${desc}<br/></#if>

好友|<@a href="/p?p_LI/${pid}/"/>亲友</a>|<@a href="/p?co_CM/${pid}/"/>师徒</a><br/>
在线/所有(${online_friend}/${total_friend})<br/>
想增加亲友度需要相互加好友<br/>
<@a href="/p?p_KF/${pid}/a/"/>游戏解答</a><br/>
<@a href="/p?p_FF/${pid}/"/>好友查找</a><br/>
<#if (page_objs?size>0)>
	<#list page_objs as friend>		
		<@a href="/p?p_VO/${pid}/${friend.friendId}/${friend.id}/"/>${friend.name}</a>(<#if friend.level?exists>${friend.level}级</#if>${friend.location})<@a href="/p?p_DFC/${pid}/${friend.friendId}"/>删除</a><br/>
	</#list>
	<#if (page > 0)><@a href="/p?p_LF/${pid}/${page-1}/"/>上页.</a></#if>
	<#if (page < total_page-1)><@a href="/p?p_LF/${pid}/${page+1}/"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
	您现在没有好友，您可以添加好友：<br/>
</#if>
<@a href="/p?p_LP/${pid}/1/0"/>活跃玩家</a><br/>
<@a href="/p?h_SM/${pid}/list/"/>我的仇人</a><br/>
<@a href="/p?p_LB/${pid}/"/>我的黑名单</a><br/>
<@goback />
<@gogame />
</p></card>
</wml>