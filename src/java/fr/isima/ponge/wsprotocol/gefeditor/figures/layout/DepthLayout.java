/* 
 * Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
 * Copyright 2005-2008 Universite Blaise Pascal, LIMOS, Clermont-Ferrand, France.
 * Copyright 2005-2008 The University of New South Wales, Sydney, Australia.
 * 
 * This file is part of ServiceMosaic Protocols <http://servicemosaic.isima.fr/>.
 * 
 * ServiceMosaic Protocols is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ServiceMosaic Protocols is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with ServiceMosaic Protocols.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.isima.ponge.wsprotocol.gefeditor.figures.layout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.State;
import fr.isima.ponge.wsprotocol.gefeditor.uiparts.ModelExtraPropertiesConstants;
import fr.isima.ponge.wsprotocol.gefeditor.util.image.ImageSizeAndProtocol;

public class DepthLayout
{
    private int xOffset = 0;
    private int maxYOffset = 0;
    
    // Dimensions for drawing picture
    // Default values are for the plugin
    private int offsetIncrement = 350;
    private int rectangleWidth = 100;
    private int rectangleHeight = 60;
    
    
    
    public BusinessProtocol layout (BusinessProtocol protocol)
    {
        List depthLayers = new ArrayList();
        
        Set curLayer = new HashSet();
        Set succLayer = new HashSet();
        Set visited = new HashSet();
        curLayer.add(protocol.getInitialState());
        visited.add(protocol.getInitialState());
        succLayer.addAll(protocol.getInitialState().getSuccessors());
        State stateForSucc; 
        while (!curLayer.isEmpty())
        {
            depthLayers.add(curLayer);
            curLayer = new HashSet();
            // We won't add the already layered states
            Iterator it = succLayer.iterator();
            while (it.hasNext())
            {
            	stateForSucc = (State)it.next();
            	if (!visited.contains(stateForSucc))
            	{
            		curLayer.add(stateForSucc);
            	}
            }
            succLayer = new HashSet();
            
            it = curLayer.iterator();
            while (it.hasNext())
            {
                State state = (State) it.next();
                if (visited.contains(state))
                {
                    continue;
                }
                succLayer.addAll(state.getSuccessors());
                visited.add(state);
            }
        }
        
        Iterator layersIt = depthLayers.iterator();
        xOffset = 10;
        maxYOffset = 10;
        while (layersIt.hasNext())
        {
            Set states = (Set) layersIt.next();
            int yOffset = 20;
            Iterator statesIt = states.iterator();
            while (statesIt.hasNext())
            {   
                State state = (State) statesIt.next();
                state.putExtraProperty(ModelExtraPropertiesConstants.STATE_WIDTH_PROP, Integer.toString(rectangleWidth));
                state.putExtraProperty(ModelExtraPropertiesConstants.STATE_HEIGHT_PROP, Integer.toString(rectangleHeight));
                state.putExtraProperty(ModelExtraPropertiesConstants.STATE_X_PROP, Integer.toString(xOffset));
                state.putExtraProperty(ModelExtraPropertiesConstants.STATE_Y_PROP, Integer.toString(yOffset));
                yOffset = yOffset + offsetIncrement;
            }
            xOffset = xOffset + offsetIncrement;
            
            if (yOffset>maxYOffset)
            	maxYOffset = yOffset;
        }
        
        return protocol;
    }
    
    public ImageSizeAndProtocol layoutForImage(BusinessProtocol protocol)
    {
    	// Change default values for drawing Image for WebApp
    	// because we need smaller images
    	offsetIncrement = 200;
        rectangleWidth = 80;
        rectangleHeight = 45;
    	
        BusinessProtocol prot = layout(protocol); 
        // For x axys : offsetIncrementForImage - 110 : because we had a rectangle, so 100, plus 10 as margis
        // For y axys : offsetIncrementForImage - 90 : because we had a rectangle, so 60, plus 20 as margis
    	return new ImageSizeAndProtocol(xOffset - (offsetIncrement - 110), maxYOffset - (offsetIncrement - 90), prot);
    }
    
}
