package org.neo4j.examples.osgi;
/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import static org.ops4j.pax.exam.CoreOptions.autoWrap;
import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.equinox;
import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.profile;
import static org.ops4j.pax.exam.CoreOptions.provision;
import static org.ops4j.pax.exam.CoreOptions.repository;
import static org.ops4j.pax.tinybundles.core.TinyBundles.bundle;
import static org.ops4j.pax.tinybundles.core.TinyBundles.withBnd;

import org.junit.Ignore;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.index.Index;
import org.ops4j.pax.exam.player.Player;
import org.ops4j.pax.exam.testforge.BundlesInState;
import org.ops4j.pax.exam.testforge.WaitForService;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

public class OSGiTest {

    @Test
    public void neo4jStartupTest()
        throws Exception
    {
        Player player = new Player().with(
            options(
                autoWrap(),
                felix(),
                equinox(),
                repository("https://oss.sonatype.org/content/groups/ops4j/"),
                cleanCaches(),
                //mavenBundle().groupId( "org.ops4j.pax.logging" ).artifactId( "pax-logging-service" ).version( "1.6.2" ),
                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-osgi-bundle" ).version( "0.1-SNAPSHOT" ),
                mavenBundle().groupId( "org.apache.geronimo.specs" ).artifactId( "geronimo-jta_1.1_spec" ).version( "1.1.1" ),
                provision( bundle()
                        .add (Neo4jActivator.class )
                        .add (MyRelationshipTypes.class )
                        .set( Constants.BUNDLE_ACTIVATOR, Neo4jActivator.class.getName() )
                        .build( withBnd() ) )
            )
        );
        test(player);

    }
    
    @Test
    public void bundleTest()
        throws Exception
    {
        Player player = new Player().with(
            options(
                autoWrap(),
               // vmOptions("-Xdebug -Xrunjdwp:transport=dt_socket,address=127.0.0.1:8000"),
                repository("https://oss.sonatype.org/content/groups/ops4j/"),
                felix(),
                equinox(),
                cleanCaches(),
                //mavenBundle().groupId( "org.ops4j.pax.logging" ).artifactId( "pax-logging-service" ).version( "1.6.2" ),
                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-osgi-bundle" ).version( "0.1-SNAPSHOT" ),
                mavenBundle().groupId( "org.neo4j.examples.osgi" ).artifactId( "test-bundle" ).version( "0.1-SNAPSHOT" ),
                mavenBundle().groupId( "org.apache.geronimo.specs" ).artifactId( "geronimo-jta_1.1_spec" ).version( "1.1.1" )
            )
        );
        test(player);

    }

    @Ignore
    @Test
    public void bundleSdnTest()
        throws Exception
    {
        Player player = new Player().with(
            options(
                autoWrap(),
                equinox(),
               // vmOptions("-Xdebug -Xrunjdwp:transport=dt_socket,address=127.0.0.1:8000"),
                repository("https://oss.sonatype.org/content/groups/ops4j/"),
                profile("spring.dm").version("1.2.1"),
                cleanCaches(),
                //mavenBundle().groupId( "org.ops4j.pax.logging" ).artifactId( "pax-logging-service" ).version( "1.6.2" ),
                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-osgi-bundle" ).version( "0.1-SNAPSHOT" ),
                mavenBundle().groupId( "net.sourceforge.cglib" ).artifactId( "com.springsource.net.sf.cglib" ).version( "2.2.0" ),
                mavenBundle().groupId( "org.aspectj" ).artifactId( "com.springsource.org.aspectj.runtime" ).version( "1.6.8.RELEASE" ),
                mavenBundle().groupId( "org.aopalliance" ).artifactId( "com.springsource.org.aopalliance" ).version( "1.0.0" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-context" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-beans" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-core" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-tx" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-aop" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-expression" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-asm" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-aspects" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.apache.geronimo.specs" ).artifactId( "geronimo-jta_1.1_spec" ).version( "1.1.1" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "com.springsource.org.apache.commons.beanutils" ).version( "1.8.0" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "com.springsource.org.apache.commons.collections" ).version( "3.2.1" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "com.springsource.org.apache.commons.codec" ).version( "1.4.0" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "com.springsource.org.apache.commons.digester" ).version( "1.8.1" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "net.junisphere.commons-jxpath" ).version( "1.3" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "com.springsource.org.apache.commons.lang" ).version( "2.5.0" ),
                mavenBundle().groupId( "org.apache.ant" ).artifactId( "com.springsource.org.apache.tools.ant" ).version( "1.8.1" ),
                mavenBundle().groupId( "commons-configuration" ).artifactId( "commons-configuration" ).version( "1.6" ),
                mavenBundle().groupId( "javax.servlet" ).artifactId( "javax.servlet" ).version( "3.0.0.v201103241009" ),
                mavenBundle().groupId( "javax.servlet" ).artifactId( "javax.servlet.jsp" ).version( "2.2.0.v201103241009" ),
                mavenBundle().groupId( "javax.el" ).artifactId( "javax.el" ).version( "2.2.0.v201105051105" ),
                mavenBundle().groupId( "javax.mail" ).artifactId( "com.springsource.javax.mail" ).version( "1.4.1" ),
                mavenBundle().groupId( "org.jdom" ).artifactId( "com.springsource.org.jdom" ).version( "1.1.0" ),
                mavenBundle().groupId( "org.objectweb.jotm" ).artifactId( "com.springsource.org.objectweb.jotm" ).version( "2.0.10" ),
                mavenBundle().groupId( "org.objectweb.howl" ).artifactId( "com.springsource.org.objectweb.howl" ).version( "1.0.2" ),
                mavenBundle().groupId( "org.springframework.data" ).artifactId( "spring-data-commons-core" ).version( "1.2.0.BUILD-SNAPSHOT" ),
                mavenBundle().groupId( "org.springframework.data" ).artifactId( "spring-data-neo4j" ).version( "2.0.0.BUILD-SNAPSHOT" ),
                mavenBundle().groupId( "org.springframework.data" ).artifactId( "spring-data-neo4j-aspects" ).version( "2.0.0.BUILD-SNAPSHOT" ),
                mavenBundle().groupId( "org.neo4j.examples.osgi" ).artifactId( "sdn-test-bundle" ).version( "0.0.1.BUILD-SNAPSHOT" )
            )
        );
        test(player);
    }

    private void test(Player player) throws Exception
    {
        player.test( WaitForService.class, GraphDatabaseService.class.getName(), 5000 )
        .test( WaitForService.class, Index.class.getName(), 5000 )
        //.test( CountBundles.class,  36)
        .test( BundlesInState.class, Bundle.ACTIVE, Bundle.ACTIVE )
        .play();
    }
}
