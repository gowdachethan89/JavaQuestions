# Toll Booth Speed Detection Problem

## Overview
We need to analyze toll booth logs on a divided highway to identify vehicles that drive at unsafe speeds.

## Highway Structure
- **ENTRY (E)**: Toll booth where vehicles enter the highway
- **EXIT (X)**: Toll booth where vehicles exit the highway
- **MAINROAD (M)**: Sensors that record license plates at full speed
- **Distance**: Toll booths are placed every 10 km

## Complete Journey Definition
A complete journey consists of:
1. Entering through an ENTRY toll booth
2. Passing through 0 or more MAINROAD toll booths
3. Exiting through an EXIT toll booth

Example:
```
90750.191 JOX304 250E ENTRY      ← Journey starts
91081.684 JOX304 260E MAINROAD   ← Driving on highway
91483.251 JOX304 270E MAINROAD   ← Still on highway
91874.493 JOX304 280E EXIT       ← Journey ends
```

## Speed Violation Criteria
A vehicle is flagged as a speeder if **any** of these conditions occur during a journey:

### Condition 1: Single Segment Speeding
- **Speed ≥ 130 km/h** in ANY 10 km segment

### Condition 2: Multiple Segment Speeding
- **Speed ≥ 120 km/h** in ANY **TWO or more** 10 km segments

## Speed Calculation Formula
```
Speed (km/h) = (Distance × 3600) / Time (seconds)
Speed (km/h) = (10 km × 3600) / time_between_booths
```

### Example Calculation
- Time between two booths: 275 seconds
- Distance: 10 km
- Speed = (10 × 3600) / 275 = 130.91 km/h ✓ **VIOLATION** (≥130)

## Important Notes
1. **Multiple journeys per vehicle**: A license plate may have multiple journeys; each speeding journey is counted separately
2. **Off-highway driving ignored**: No speeding check between EXIT and ENTRY events
3. **One violation per journey**: Even if multiple conditions are met, the journey is only flagged once
4. **Result format**: The `catchSpeeders()` function returns a list where each entry represents ONE speeding journey

## Example Results
If vehicle "ABC123" drives unsafely in 2 different journeys:
- Result: `["ABC123", "ABC123"]` (appears twice)

If vehicle "XYZ789" has one safe and one speeding journey:
- Result: `["XYZ789"]` (appears once, only for the speeding journey)
