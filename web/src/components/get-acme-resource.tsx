'use client'

import { useState } from 'react'
import { Button } from './ui/button'
import { getResource } from '@/lib/data/queries/get-resource'

export function GetAcmeResource() {
  const [isLoading, setIsLoading] = useState(false)
  const [acmeResource, setAcmeResource] = useState<any>(undefined)
  const [error, setError] = useState<string | null>(null)

  const handleGetAcmeResource = async () => {
    setIsLoading(true)
    const [error, resource] = await getResource()

    if (error) {
      if (error.statusCode === 403) {
        setError('You do not have permission to access this resource.')
      } else {
        setError(
          'An error occurred while fetching the resource. Please try again later.'
        )
      }
    } else {
      setAcmeResource(resource)
    }

    setIsLoading(false)
  }

  return (
    <div>
      <Button
        variant="outline"
        className="w-full my-2"
        onClick={handleGetAcmeResource}
        disabled={isLoading}
      >
        {isLoading ? 'Loading...' : 'Get Acme Resource'}
      </Button>
      {error && <p>{error}</p>}
      {acmeResource && (
        <pre style={{ whiteSpace: 'pre-wrap' }}>
          {JSON.stringify(acmeResource, null, 2)}
        </pre>
      )}
    </div>
  )
}
