/*
 * JBoss DNA (http://www.jboss.org/dna)
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * See the AUTHORS.txt file in the distribution for a full listing of 
 * individual contributors. 
 *
 * JBoss DNA is free software. Unless otherwise indicated, all code in JBoss DNA
 * is licensed to you under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * JBoss DNA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.dna.graph.request;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import org.jboss.dna.graph.request.CopyBranchRequest;
import org.jboss.dna.graph.request.ReadNodeRequest;
import org.jboss.dna.graph.request.Request;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Randall Hauch
 */
public class ReadNodeRequestTest extends AbstractRequestTest {

    private ReadNodeRequest request;

    @Override
    @Before
    public void beforeEach() {
        super.beforeEach();
    }

    @Override
    protected Request createRequest() {
        return new ReadNodeRequest(validPathLocation1);
    }

    @Test( expected = IllegalArgumentException.class )
    public void shouldNotAllowCreatingRequestWithNullFromLocation() {
        new CopyBranchRequest(null, validPathLocation);
    }

    @Test( expected = IllegalArgumentException.class )
    public void shouldNotAllowCreatingRequestWithNullToLocation() {
        new CopyBranchRequest(validPathLocation, null);
    }

    @Test
    public void shouldCreateValidRequestWithValidLocation() {
        request = new ReadNodeRequest(validPathLocation1);
        assertThat(request.at(), is(sameInstance(validPathLocation1)));
        assertThat(request.hasError(), is(false));
        assertThat(request.getError(), is(nullValue()));
    }

    @Test
    public void shouldConsiderEqualTwoRequestsWithSameLocations() {
        request = new ReadNodeRequest(validPathLocation1);
        ReadNodeRequest request2 = new ReadNodeRequest(validPathLocation1);
        assertThat(request, is(request2));
    }

    @Test
    public void shouldConsiderNotEqualTwoRequestsWithDifferentLocations() {
        request = new ReadNodeRequest(validPathLocation1);
        ReadNodeRequest request2 = new ReadNodeRequest(validPathLocation2);
        assertThat(request.equals(request2), is(false));
    }

    @Test
    public void shouldAllowAddingChildren() {
        request = new ReadNodeRequest(validPathLocation);
        request.addChild(validPathLocation1);
        request.addChild(validPathLocation2);
        assertThat(request.getChildren().size(), is(2));
        assertThat(request.getChildren(), hasItems(validPathLocation1, validPathLocation2));
    }

    @Test
    public void shouldAllowAddingProperties() {
        request = new ReadNodeRequest(validPathLocation);
        request.addProperty(validProperty1);
        request.addProperty(validProperty2);
        assertThat(request.getProperties().size(), is(2));
        assertThat(request.getProperties(), hasItems(validProperty1, validProperty2));
        assertThat(request.getPropertiesByName().get(validProperty1.getName()), is(validProperty1));
        assertThat(request.getPropertiesByName().get(validProperty2.getName()), is(validProperty2));
    }
}