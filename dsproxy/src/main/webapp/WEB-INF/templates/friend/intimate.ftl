<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if desc?exists>${desc}<br/></#if>

<@a href="/p?p_LF/${pid}/0/"/>好友</a>|亲友|<@a href="/p?co_CM/${pid}/"/>师徒</a><br/>
在线/所有(${online_intimate}/${total_intimate})<br/>
<@a href="/p?p_R/${pid}/3"/>玫瑰榜</a><br/>
<@a href="/p?p_R/${pid}/4"/>浓情玫瑰榜</a><br/>
<@a href="/p?p_R/${pid}/5"/>巧克力玫瑰榜</a><br/>
<@a href="/p?p_R/${pid}/6"/>推荐榜</a><br/>
<#if (page_objs?size>0)>
	一起在线玩游戏可以增加亲友度，送礼也可以增加亲友度,当亲友度达到1000时可以到小方盘城月老阁了解婚姻系统。<br/>
	<#list page_objs as friend>
	<@a href="/p?p_VO/${pid}/${friend.friendId}/${friend.id}/"/>${friend.name}</a>(<#if friend.level?exists>${friend.level}级</#if>${friend.location})(亲友度${friend.intimateScore})<br/>
	</#list>
	<#if (page>0)><@a href="/p?p_LI/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?p_LI/${pid}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
亲友是指互相都加了好友的伙伴，成为亲友之后，你的亲友上线你都会收到她的上线通知，成为亲友之后会有亲友度，异性之间亲友度达到1000之后可以申请结婚<br/>
</#if>
<@goback />
<@gogame />
</p>
</card>
</wml>